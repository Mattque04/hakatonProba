package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class FeatureScreenView(
    private val navigator: UiNavigator,
    private val actions: MainActions,
    private val feature: Feature
) {
    fun component(): JComponent = panel {
        group(feature.label) {

            row {
                button("← Back") { navigator.showChooser() }
            }

            group("Function context") {
                row("Selected function:") {
                    label("calculatePrice(Order)").bold()
                }.comment("Pick a function in the editor, then run this feature.")
            }

            group("Parameters") {
                when (feature) {
                    Feature.SUMMARIZE -> {
                        row("Days back:") { intTextField(7..365) }
                        row("Max commits:") { intTextField(1..200) }
                    }
                    Feature.COMPARE -> {
                        row("N commits:") { intTextField(1..200) }
                    }
                    Feature.TIMELINE -> {
                        row("Days back:") { intTextField(1..365) }
                    }
                }
            }

            row {
                button(runLabel(feature)) {
                    actions.onActionSelected(feature.label)
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }

    private fun runLabel(feature: Feature): String = when (feature) {
        Feature.SUMMARIZE -> "Run summary"
        Feature.COMPARE -> "Compare"
        Feature.TIMELINE -> "Build timeline"
    }
}