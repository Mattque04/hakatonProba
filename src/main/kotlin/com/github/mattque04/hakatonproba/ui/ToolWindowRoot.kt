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
            chatView.append("System: (placeholder result)\n\n")
        }

        override fun onSendMessage(text: String) {
            chatView.append("You: $text\n")
            chatView.append("Assistant: (placeholder response)\n\n")
        }
    }

    fun component(): JComponent {
        val chooser = FeatureChooserView(this)
        val summarize = SummarizeFeatureView(this, controller)
        val compare = CompareFeatureView(this, controller)
        val timeline = TimelineFeatureView(this, controller)

        chatView = ChatView(this, controller)

        root.add(chooser.component(), "chooser")
        root.add(summarize.component(), "summarize")
        root.add(compare.component(), "compare")
        root.add(timeline.component(), "timeline")
        root.add(chatView.component(), "chat")

        showChooser()
        return root
    }

    override fun showChooser() = cards.show(root, "chooser")
    override fun showSummarize() = cards.show(root, "summarize")
    override fun showCompare() = cards.show(root, "compare")
    override fun showTimeline() = cards.show(root, "timeline")
    override fun showChat() = cards.show(root, "chat")
}