package com.github.mattque04.hakatonproba.ui

import com.github.mattque04.hakatonproba.core.GitUtils
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class CompareFeatureView(
    private val navigator: UiNavigator,
    private val actions: MainActions
) {
    fun component(): JComponent = panel {
        group("Compare") {
            // 1️⃣ Dugme za povratak
            row {
                button("← Back") { navigator.showChooser() }
            }

            // 2️⃣ Dropdown sa svim granama
            val branches = GitUtils.getAllBranches(
                System.getProperty("user.dir")
            )
            val comboModel = javax.swing.DefaultComboBoxModel(branches.toTypedArray())
            val branchDropdown = javax.swing.JComboBox(comboModel)

            row("Select branch:") {
                cell(branchDropdown)
            }

            // 3️⃣ Dugme Compare
            row {
                button("Compare") {
                    val selectedBranch = branchDropdown.selectedItem as? String ?: return@button
                    val req = ActionRequest.Compare(
                        targetBranch = selectedBranch
                    )
                    actions.onActionSelected(req)
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}