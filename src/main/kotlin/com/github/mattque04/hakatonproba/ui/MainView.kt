/*package com.github.mattque04.hakatonproba.ui


import javax.swing.JComponent
import com.intellij.ui.dsl.builder.TopGap


import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel

class MainView(
    private val actions: MainActions
) {
    fun component(): JComponent = panel {
        group("Function context") {
            row("Selected function:") {
                label("calculatePrice(Order)").bold()
            }//.comment("Pick a function in the editor, then summarize what changed in Git.")

            separator()

            row {
                button("Compare last 10 commits") {
                    actions.onActionSelected("Compare last 10 commits")
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)//.comment("AI summary of changes affecting this function in the selected time range.")

            row {
                button("Give me rebase head for this branch") {
                    actions.onActionSelected("Give me rebase head for this branch")
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)

            row {
                button("Summers me last pull request on main") {
                    actions.onActionSelected("Summers me last pull request on main")
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}

 */