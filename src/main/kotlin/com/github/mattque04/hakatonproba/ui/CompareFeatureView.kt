package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class CompareFeatureView(
    private val navigator: UiNavigator,
    private val actions: MainActions
) {
    fun component(): JComponent = panel {
        group("Compare") {
            row { button("← Back") { navigator.showChooser() } }

            group("Function context") {
                row("Selected function:") { label("calculatePrice(Order)").bold() }
                row { comment("Compare changes to this function across commits.") }
            }

            group("Parameters") {
                row("N commits:") { intTextField(1..200) }
            }

            row {
                button("Compare") {
                    actions.onActionSelected("Compare")
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}