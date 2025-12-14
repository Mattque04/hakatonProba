package com.github.mattque04.hakatonproba.ui

import com.github.mattque04.hakatonproba.core.GitUtils
import com.intellij.ui.components.JBPanel
import java.awt.CardLayout
import javax.swing.JComponent

class ToolWindowRoot : UiNavigator {

    private val cards = CardLayout()
    private val root = JBPanel<JBPanel<*>>(cards)

    private lateinit var chatView: ChatView
    private lateinit var controller: ControllerImpl

    private lateinit var summarize: SummarizeFeatureView
    private lateinit var chooser: FeatureChooserView
    private lateinit var compare: CompareFeatureView
    private lateinit var timeline: TimelineFeatureView

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
        chooser = FeatureChooserView(this)
        compare = CompareFeatureView(this, controller)
        timeline = TimelineFeatureView(this, controller)
        summarize = SummarizeFeatureView(this, controller)

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
    override fun showCompare() {

        // 1️⃣ Dobavi putanju projekta
        val projectPath = System.getProperty("user.dir")

        // 2️⃣ Učitaj sve grane
        val branches = GitUtils.getAllBranches(projectPath)

        // 3️⃣ Napravi dropdown
        val comboModel = javax.swing.DefaultComboBoxModel(branches.toTypedArray())
        val branchDropdown = javax.swing.JComboBox(comboModel)

        // 4️⃣ Dodaj u UI (primer)
        root.add(branchDropdown)
        root.revalidate()
        root.repaint()
        cards.show(root, "compare")

    }
    override fun showTimeline() = cards.show(root, "timeline")
    override fun showChat() = cards.show(root, "chat")
}