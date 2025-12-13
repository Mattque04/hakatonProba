package com.github.mattque04.hakatonproba.ui

import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.*

class ChatView(
    private val navigator: UiNavigator,
    private val chatActions: ChatActions
) {
    private val historyView = ChatHistoryView()
    private val inputView = ChatInputView(chatActions)

    fun component(): JComponent =
        JPanel(BorderLayout(0, 8)).apply {
            add(topBar(), BorderLayout.NORTH)
            add(historyView.component(), BorderLayout.CENTER)
            add(inputView.component(), BorderLayout.SOUTH)
        }

    fun append(text: String) = historyView.append(text)

    private fun topBar(): JComponent =
        JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            add(JButton("← Back").apply { addActionListener { navigator.showMain() } })
            add(JLabel("CHAT").apply { font = font.deriveFont(Font.BOLD) })
        }
}