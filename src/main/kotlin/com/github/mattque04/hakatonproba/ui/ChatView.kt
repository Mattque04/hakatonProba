package com.github.mattque04.hakatonproba.ui

import com.github.mattque04.hakatonproba.openai.OpenAi
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.openai.models.ChatModel
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.Font
import javax.swing.*

class ChatView(
    private val navigator: UiNavigator,
    private var chatActions: ChatActions
)
{
    private val historyView = ChatHistoryView()
    private val inputView = ChatInputView(chatActions, this)
    public var responseId: String? = null;

    fun component(): JComponent =
        JPanel(BorderLayout(0, 8)).apply {
            add(topBar(), BorderLayout.NORTH)
            add(historyView.component(), BorderLayout.CENTER)
            add(inputView.component(), BorderLayout.SOUTH)
        }

    fun append(text: String) = historyView.append(text)


    fun setActions(actions: ChatActions) {
        this.chatActions = actions
    }

    private fun topBar(): JComponent =
        JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            add(JButton("← Back").apply { addActionListener { navigator.showChooser() } })
            add(JLabel("CHAT").apply { font = font.deriveFont(Font.BOLD) })
        }

    fun clear() {
        historyView.clear()
    }

    fun addUserMessage(string: String) {
        historyView.append("$string\n")
        object : Task.Backgroundable(
            GlobalVariables.selectedElement!!.project, "My Background Job", false
        ) {
            override fun run(indicator: ProgressIndicator) {
                var resp = OpenAi().call(
                    "",
                    string,
                    ChatModel.GPT_4O,
                    0.7,
                    responseId
                )

                responseId = resp.responseId
                print("aaaa " + resp.responseId)

                historyView.append("${resp.result}\n")
            }
        }.queue()
    }
}