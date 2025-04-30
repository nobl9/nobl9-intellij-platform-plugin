package com.nobl9.lsp

import com.goide.sdk.GoSdkService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.LspServerSupportProvider.LspServerStarter
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import java.io.File

class Nobl9LanguageServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(project: Project, file: VirtualFile, serverStarter: LspServerStarter) {

        val templConfigService = Nobl9Settings.getService(project)
        if (file.extension != "templ") return
        val executable = File(templConfigService.getTemplLspPath())
        if (!executable.exists()) return
        serverStarter.ensureServerStarted(Nobl9LanguageServerDescriptor(project, executable))
    }
}

private class Nobl9LanguageServerDescriptor(project: Project, val executable: File) :
    ProjectWideLspServerDescriptor(project, "templ") {
    override fun isSupportedFile(file: VirtualFile) = file.extension == "templ"
    override fun createCommandLine(): GeneralCommandLine {
        val cmd = GeneralCommandLine(executable.absolutePath, "lsp")
        val settings = Nobl9Settings.getService(project).state
        if (settings.goplsLog.isNotEmpty()) cmd.addParameter("-goplsLog=${settings.goplsLog}")
        if (settings.log.isNotEmpty()) cmd.addParameter("-log=${settings.log}")
        if (settings.http.isNotEmpty()) cmd.addParameter("-http=${settings.http}")
        if (settings.goplsRPCTrace) cmd.addParameter("-goplsRPCTrace=true")
        if (settings.pprof) cmd.addParameter("-pprof=true")

        val goPath = GoSdkService.getInstance(project).getSdk(null).executable?.parent?.path
        val currentPath = System.getenv("PATH").orEmpty()
        goPath?.let { cmd.withEnvironment("PATH", "$it:$currentPath") }

        return cmd
    }
}
