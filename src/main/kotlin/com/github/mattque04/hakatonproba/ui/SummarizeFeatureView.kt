package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class SummarizeFeatureView(
    private val navigator: UiNavigator,
    private val actions: MainActions
) {
    fun component(): JComponent = panel {
        group("Summarize") {
            row { button("← Back") { navigator.showChooser() } }

            group("Function context") {
                row("Selected function:") { label("calculatePrice(Order)").bold() }
                row { comment("Pick a function in the editor, then run summary.") }
            }

            group("Parameters") {
                row("Days back:") { intTextField(7..365) }
                row("Max commits:") { intTextField(1..200) }
            }

            row {
                button("Run summary") {
                    actions.onActionSelected("Summarize")
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}