package com.nobl9.nobl9.nobl9

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.LspServerSupportProvider.LspServerStarter
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor
import java.io.File

const val PLUGIN_NAME: String = "nobl9"
const val YAML_EXTENSION: String = "yaml"

class Nobl9LanguageServerSupportProvider : LspServerSupportProvider {
    override fun fileOpened(project: Project, file: VirtualFile, serverStarter: LspServerStarter) {
        val configService = Nobl9Settings.getService(project)
        if (file.extension != YAML_EXTENSION) {
            return
        }
        val executable = File(configService.getLanguageServerExecutablePath())
        if (!executable.exists()) {
            NotificationGroupManager.getInstance()
                .getNotificationGroup("Nobl9")
                .createNotification(
                    "Nobl9 language server executable not found at: ${executable.absolutePath}",
                    NotificationType.ERROR
                ).notify(project)

            return
        }
        serverStarter.ensureServerStarted(Nobl9LanguageServerDescriptor(project, executable))
    }
}

private class Nobl9LanguageServerDescriptor(project: Project, val executable: File) :
    ProjectWideLspServerDescriptor(project, PLUGIN_NAME) {

    override fun isSupportedFile(file: VirtualFile): Boolean {
        return file.extension == YAML_EXTENSION
    }

    override fun createCommandLine(): GeneralCommandLine {
        val cmd = GeneralCommandLine(executable.absolutePath)
        val settings = Nobl9Settings.getService(project).state
        if (settings.logLevel.isNotEmpty()) cmd.addParameter("-logLevel=${settings.logLevel}")
        if (settings.logFilePath.isNotEmpty()) cmd.addParameter("-logFilePath=${settings.logFilePath}")
        return cmd
    }
}
