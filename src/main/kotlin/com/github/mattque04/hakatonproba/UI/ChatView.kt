package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.components.JBTextField
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ChatView(
    private val navigator: UiNavigator,
    private val controller: ChatController
) {

    private val history = JBTextArea().apply {
        isEditable = false
        lineWrap = true
        wrapStyleWord = true
    }
    private val input = JBTextField()

    fun component(): JComponent {
        val topBar = JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            add(JButton("← Back").apply { addActionListener { navigator.showMain() } })
            add(JLabel("CHAT").apply { font = font.deriveFont(Font.BOLD) })
        }

        val center = JBScrollPane(history)

        val sendBtn = JButton("Send").apply {
            addActionListener {
                val text = input.text?.trim().orEmpty()
                if (text.isNotEmpty()) {
                    controller.onSendMessage(text)
                    input.text = ""
                }
            }
        }

        val bottom = JPanel(BorderLayout(8, 8)).apply {
            add(input, BorderLayout.CENTER)
            add(sendBtn, BorderLayout.EAST)
        }

        return JPanel(BorderLayout(0, 8)).apply {
            add(topBar, BorderLayout.NORTH)
            add(center, BorderLayout.CENTER)
            add(bottom, BorderLayout.SOUTH)
        }
    }

    fun append(text: String) {
        history.append(text)
        history.caretPosition = history.document.length
    }
}