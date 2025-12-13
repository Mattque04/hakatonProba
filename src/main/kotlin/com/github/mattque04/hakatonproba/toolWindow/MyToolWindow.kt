package com.github.mattque04.hakatonproba.toolWindow

import com.github.mattque04.hakatonproba.MyBundle
import com.github.mattque04.hakatonproba.services.MyProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import javax.swing.JButton

class MyToolWindow(toolWindow: ToolWindow) {

    private val service = toolWindow.project.service<MyProjectService>()

    fun getContent() = JBPanel<JBPanel<*>>().apply {
        layout = javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS)

        // Header
        val titleLabel = JBLabel("Function context").apply {
            font = font.deriveFont(java.awt.Font.BOLD)
        }
        add(titleLabel)
        add(javax.swing.Box.createVerticalStrut(8))

        // Function info
        add(JBLabel("Selected function:"))
        val functionLabel = JBLabel("calculatePrice(Order)").apply {
            font = font.deriveFont(java.awt.Font.BOLD)
        }
        add(functionLabel)
        add(javax.swing.Box.createVerticalStrut(12))

        // Actions
        add(JButton("Summarize changes").apply {
            addActionListener {
                // TODO: hook later
            }
        })

        add(javax.swing.Box.createVerticalStrut(4))

        add(JButton("Compare last N commits").apply {
            addActionListener {
                // TODO
            }
        })

        add(javax.swing.Box.createVerticalStrut(4))

        add(JButton("Show timeline").apply {
            addActionListener {
                // TODO
            }
        })
    }
}