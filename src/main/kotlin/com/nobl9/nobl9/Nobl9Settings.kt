package com.nobl9.nobl9

import com.intellij.execution.configurations.PathEnvironmentVariableUtil
import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Nobl9State {
    var executable = ""
    var logLevel = "trace"
    var logFilePath = "/usr/local/bin/n9.log"
}

const val LANGUAGE_SERVER_NAME: String = "nobl9-language-server"

@Service(Service.Level.PROJECT)
@State(name = "Nobl9Settings", storages = [Storage("nobl9.xml")])
class Nobl9Settings : PersistentStateComponent<Nobl9State> {
    companion object {
        fun getService(project: Project): Nobl9Settings = project.service<Nobl9Settings>()
    }

    private var state = Nobl9State()

    override fun getState(): Nobl9State {
        return state
    }

    override fun loadState(state: Nobl9State) {
        this.state = state
    }

    fun getLanguageServerExecutablePath(): String {
        val languageServerPath = this.state.executable
        if (languageServerPath.isEmpty()) {
            return findGlobalNobl9Executable()?.absolutePath ?: ""
        }
        return languageServerPath
    }
}

fun findGlobalNobl9Executable(): File? {
    PathEnvironmentVariableUtil.findExecutableInPathOnAnyOS(LANGUAGE_SERVER_NAME)?.let { return it }
    for (loc in languageServerExecutableLocations) {
        if (Files.exists(loc)) {
            return loc.toFile()
        }
    }
    return null
}

private val languageServerExecutableLocations = arrayOf(
    System.getenv("GOBIN")?.let { Paths.get(it, LANGUAGE_SERVER_NAME) },
    System.getenv("GOBIN")?.let { Paths.get(it, "$LANGUAGE_SERVER_NAME.exe") },
    System.getenv("GOPATH")?.let { Paths.get(it, "bin", LANGUAGE_SERVER_NAME) },
    System.getenv("GOPATH")?.let { Paths.get(it, "bin", "$LANGUAGE_SERVER_NAME.exe") },
    System.getenv("GOROOT")?.let { Paths.get(it, "bin", LANGUAGE_SERVER_NAME) },
    System.getenv("GOROOT")?.let { Paths.get(it, "bin", "$LANGUAGE_SERVER_NAME.exe") },
    System.getenv("HOME")?.let { Paths.get(it, "bin", LANGUAGE_SERVER_NAME) },
    System.getenv("HOME")?.let { Paths.get(it, "bin", "$LANGUAGE_SERVER_NAME.exe") },
    System.getenv("HOME")?.let { Paths.get(it, "go", "bin", LANGUAGE_SERVER_NAME) },
    System.getenv("HOME")?.let { Paths.get(it, "go", "bin", "$LANGUAGE_SERVER_NAME.exe") },
    Paths.get("/usr/local/bin/$LANGUAGE_SERVER_NAME"),
    Paths.get("/usr/bin/$LANGUAGE_SERVER_NAME"),
    Paths.get("/usr/local/go/bin/$LANGUAGE_SERVER_NAME"),
    Paths.get("/usr/local/share/go/bin/$LANGUAGE_SERVER_NAME"),
    Paths.get("/usr/share/go/bin/$LANGUAGE_SERVER_NAME"),
).filterNotNull()
