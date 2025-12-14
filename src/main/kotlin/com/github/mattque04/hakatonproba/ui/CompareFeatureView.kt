package com.github.mattque04.hakatonproba.ui

import com.github.mattque04.hakatonproba.core.GitUtils
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.ProjectManager
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
            //Dugme za povratak
            row {
                button("← Back") { navigator.showChooser() }
            }

            //Dropdown sa svim granama
            val branches = GitUtils.getAllBranches(
                ProjectManager.getInstance().openProjects.firstOrNull()!!.basePath!!

            )
            val comboModel = javax.swing.DefaultComboBoxModel(branches.toTypedArray())
            val branchDropdown = javax.swing.JComboBox(comboModel)

            row("Select branch:") {
                cell(branchDropdown)
            }

            //Dugme Compare
            row {
                button("Compare") {
                    object : Task.Backgroundable(
                        ProjectManager.getInstance().openProjects.firstOrNull(), "My Background Job", false
                    ) {
                        override fun run(indicator: ProgressIndicator) {
                            ReadAction.run<RuntimeException> {
                                val selectedBranch = branchDropdown.selectedItem?.toString() ?: ""
                                if(selectedBranch == "")
                                    return@run
                                val req = ActionRequest.Compare(
                                    targetBranch = selectedBranch
                                )
                                actions.onActionSelected(req)
                                navigator.showChat()
                            }
                        }
                    }.queue()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}