package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import javax.swing.JButton
import javax.swing.JComponent

class TimelineFeatureView(
    private val navigator: UiNavigator,
    private val actions: MainActions
) {
    var daysBack = 14

    fun component(): JComponent = panel {
        group("Commit Summarizer") {

            //Back button
            row {
                button("← Back") { navigator.showChooser() }
            }

            //Input for commit hash
            lateinit var commitField: Cell<JBTextField>
            row("Commit ID / Hash:") {
                commitField = textField()
            }

            //Generate summary button
            row {
                button("Generate Summary") {
                    val commitId = commitField.component.text.trim()
                    if (commitId.isEmpty()) return@button

                    val req = ActionRequest.Timeline(commitId = commitId)
                    actions.onActionSelected(req)
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}