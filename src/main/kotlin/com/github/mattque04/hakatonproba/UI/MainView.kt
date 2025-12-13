package com.github.mattque04.hakatonproba.UI

import com.github.mattque04.hakatonproba.UI.ControllerImpl
import com.github.mattque04.hakatonproba.UI.MainActions
import com.intellij.ui.components.JBPanel
import java.awt.Font
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
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
            }.comment("Pick a function in the editor, then summarize what changed in Git.")

            separator()

            row {
                button("Summarize changes") {
                    actions.onActionSelected("Summarize changes")
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM).comment("AI summary of changes affecting this function in the selected time range.")

            row {
                button("Compare last N commits") {
                    actions.onActionSelected("Compare last N commits")
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)

            row {
                button("Show timeline") {
                    actions.onActionSelected("Show timeline")
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}