package com.github.mattque04.hakatonproba.ui


import com.intellij.ui.components.JBPanel
import java.awt.CardLayout
import javax.swing.JComponent

class ToolWindowRoot : UiNavigator {

    private val cards = CardLayout()
    private val root = JBPanel<JBPanel<*>>(cards)

    private lateinit var chatView: ChatView

    private val controller = object : MainActions, ChatActions {

        override fun onActionSelected(actionName: String) {
            chatView.append("System: Started action -> $actionName\n")
            chatView.append("System: (pretend we ran a job and got a summary)\n\n")
            showChat()
        }

        override fun onSendMessage(text: String) {
            chatView.append("You: $text\n")
            chatView.append("Assistant: (placeholder response)\n\n")
        }
    }

    fun component(): JComponent {
        val mainView = MainView(controller)
        chatView = ChatView(this, controller)

        root.add(mainView.component(), "main")
        root.add(chatView.component(), "chat")
        showMain()

        return root
    }

    override fun showMain() = cards.show(root, "main")
    override fun showChat() = cards.show(root, "chat")
}