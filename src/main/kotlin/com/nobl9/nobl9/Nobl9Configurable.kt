package com.nobl9.nobl9

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.NlsContexts.ConfigurableName
import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class Nobl9Configurable(private val project: Project) : Configurable {

    private val executable = TextFieldWithBrowseButton()
    private val logLevel = TextFieldWithBrowseButton()
    private val logFilePath = TextFieldWithBrowseButton()


    override fun getDisplayName(): @ConfigurableName String {
        return "Nobl9"
    }

    override fun createComponent(): JComponent {
        val service = Nobl9Settings.getService(project)
        executable.text = service.getLanguageServerExecutablePath()
        logLevel.text = service.state.logLevel
        logFilePath.text = service.state.logFilePath

        val mainFormBuilder = FormBuilder.createFormBuilder()
        mainFormBuilder.addLabeledComponent("Language server executable path", executable)
        mainFormBuilder.addLabeledComponent("Log level", logLevel)
        mainFormBuilder.addLabeledComponent("Log file path", logFilePath)
        val wrapper = JPanel(BorderLayout())
        wrapper.add(mainFormBuilder.panel, BorderLayout.NORTH)
        return wrapper
    }

    override fun isModified(): Boolean {
        val service = Nobl9Settings.getService(project)
        val state = service.state
        return executable.text != state.executable ||
                logLevel.text != state.logLevel ||
                logFilePath.text != state.logFilePath

    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        if (isModified) {
            val service = Nobl9Settings.getService(project)
            val state = service.state
            state.executable = executable.text
            state.logLevel = logLevel.text
            state.logFilePath = logFilePath.text
            service.loadState(state)
        }
    }
}