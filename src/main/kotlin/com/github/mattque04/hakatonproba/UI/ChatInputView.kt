package com.github.mattque04.hakatonproba.UI

import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import javax.swing.AbstractAction
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.KeyStroke
import javax.swing.ScrollPaneConstants
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class ChatInputView(
    private val actions: ChatActions,
    private val minRows: Int = 2,
    private val maxRows: Int = 6
) {
    private var behaviorInstalled = false

    private val input = JBTextArea(minRows, 1).apply {
        lineWrap = true
        wrapStyleWord = true
    }

    fun component(): JComponent {
        installBehaviorOnce()

        val sendBtn = JButton("Send").apply {
            addActionListener { send() }
        }

        val inputScroll = JBScrollPane(input).apply {
            horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        }

        return JPanel(BorderLayout(8, 8)).apply {
            add(inputScroll, BorderLayout.CENTER)
            add(sendBtn, BorderLayout.EAST)
        }
    }

    private fun installBehaviorOnce() {
        if (behaviorInstalled) return
        installEnterBindings()
        installAutoGrow()
        behaviorInstalled = true
    }

    private fun installEnterBindings() {
        // ENTER -> send
        input.inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "sendMessage")
        input.actionMap.put("sendMessage", object : AbstractAction() {
            override fun actionPerformed(e: ActionEvent?) {
                send()
            }
        })

        // SHIFT+ENTER -> new line
        input.inputMap.put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK),
            "insert-break"
        )
    }

    private fun installAutoGrow() {
        input.document.addDocumentListener(object : DocumentListener {
            override fun insertUpdate(e: DocumentEvent?) = resize()
            override fun removeUpdate(e: DocumentEvent?) = resize()
            override fun changedUpdate(e: DocumentEvent?) = resize()

            private fun resize() {
                val lines = input.lineCount.coerceIn(minRows, maxRows)
                input.rows = lines
                input.revalidate()
            }
        })
    }

    private fun send() {
        val text = input.text.trim()
        if (text.isNotEmpty()) {
            actions.onSendMessage(text)
            input.text = ""
            // reset back to min height after send
            input.rows = minRows
            input.revalidate()
        }
    }
}