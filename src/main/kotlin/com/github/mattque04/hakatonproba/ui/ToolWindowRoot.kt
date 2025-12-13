package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.components.JBPanel
import java.awt.CardLayout
import javax.swing.JComponent

class ToolWindowRoot : UiNavigator {

    private val cards = CardLayout()
    private val root = JBPanel<JBPanel<*>>(cards)

    private lateinit var chatView: ChatView
    private lateinit var controller: ControllerImpl

    private val summarize: SummarizeFeatureView
        get() {
            val summarize = SummarizeFeatureView(this, controller)
            return summarize
        }

    fun component(): JComponent {
        // 1) napravi chatView sa privremenim actions (biće zamenjeno odmah posle)
        val tmpActions = object : ChatActions {
            override fun onSendMessage(text: String) { /* ignore until wired */ }
        }
        chatView = ChatView(this, tmpActions)

        // 2) sad možeš da napraviš pravi controller
        controller = ControllerImpl(this, chatView)

        // 3) re-wire chat actions u ChatView (treba mala izmena u ChatView, vidi ispod)
        chatView.setActions(controller)

        // 4) feature view-ovi dobijaju MainActions = controller
        val chooser = FeatureChooserView(this)
        val summarize = SummarizeFeatureView(this, controller)
        val compare = CompareFeatureView(this, controller)
        val timeline = TimelineFeatureView(this, controller)

        root.add(chooser.component(), "chooser")
        root.add(summarize.component(), "summarize")
        root.add(compare.component(), "compare")
        root.add(timeline.component(), "timeline")
        root.add(chatView.component(), "chat")

        showChooser()
        return root
    }

    override fun showChooser() = cards.show(root, "chooser")
    override fun showSummarize() {
        summarize.setFunctionLabel(GlobalVariables.selectedElement!!.text!!)
        cards.show(root, "summarize")
    }
    override fun showCompare() = cards.show(root, "compare")
    override fun showTimeline() = cards.show(root, "timeline")
    override fun showChat() = cards.show(root, "chat")
}