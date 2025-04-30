package com.nobl9.lsp

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.execution.configurations.PathEnvironmentVariableUtil
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import java.nio.file.Files
import java.nio.file.Paths

class TemplState {
    var Path = ""
    var logLevel = ""
    var logFilePath = ""
}

@State(name = "Nobl9Settings", storages = [Storage("nobl9.xml")])
class Nobl9Settings : PersistentStateComponent<TemplState> {
    companion object {
        fun getService(project: Project): Nobl9Settings = project.service<Nobl9Settings>()
    }

    private var state = TemplState()

    override fun getState(): TemplState {
        return state
    }

    override fun loadState(state: TemplState) {
        this.state = state
    }

    fun getTemplLspPath(): String {
        val templPath = this.state.templLspPath
        if (templPath.isEmpty()) {
            return findGlobalNobl9Executable()?.absolutePath ?: ""
        }
        return templPath
    }
}

fun findGlobalNobl9Executable(): File? {
    PathEnvironmentVariableUtil.findExecutableInPathOnAnyOS("templ")?.let { return it }
    for (loc in languageServerBinaryLocations)
        if (Files.exists(loc))
            return loc.toFile()
    return null
}

private val languageServerBinaryLocations = arrayOf(
    System.getenv("GOBIN")?.let { Paths.get(it, "templ") },
    System.getenv("GOBIN")?.let { Paths.get(it, "templ.exe") },
    System.getenv("GOPATH")?.let { Paths.get(it, "bin", "templ") },
    System.getenv("GOPATH")?.let { Paths.get(it, "bin", "templ.exe") },
    System.getenv("GOROOT")?.let { Paths.get(it, "bin", "templ") },
    System.getenv("GOROOT")?.let { Paths.get(it, "bin", "templ.exe") },
    System.getenv("HOME")?.let { Paths.get(it, "bin", "templ") },
    System.getenv("HOME")?.let { Paths.get(it, "bin", "templ.exe") },
    System.getenv("HOME")?.let { Paths.get(it, "go", "bin", "templ") },
    System.getenv("HOME")?.let { Paths.get(it, "go", "bin", "templ.exe") },
    Paths.get("/usr/local/bin/templ"),
    Paths.get("/usr/bin/templ"),
    Paths.get("/usr/local/go/bin/templ"),
    Paths.get("/usr/local/share/go/bin/templ"),
    Paths.get("/usr/share/go/bin/templ"),
).filterNotNull()
