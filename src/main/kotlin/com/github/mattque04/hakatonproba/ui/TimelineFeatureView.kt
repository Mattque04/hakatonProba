package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class TimelineFeatureView(
    private val navigator: UiNavigator,
    private val actions: MainActions
) {
    fun component(): JComponent = panel {
        group("Timeline") {
            row { button("← Back") { navigator.showChooser() } }

            group("Function context") {
                row("Selected function:") { label("calculatePrice(Order)").bold() }
                row { comment("Build a timeline of commits affecting this function.") }
            }

            group("Parameters") {
                row("Days back:") { intTextField(1..365) }
            }

            row {
                button("Build timeline") {
                    actions.onActionSelected("Timeline")
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}