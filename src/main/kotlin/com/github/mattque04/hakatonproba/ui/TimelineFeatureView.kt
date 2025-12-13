package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class TimelineFeatureView(
    private val navigator: UiNavigator,
    private val actions: MainActions
) {
    var daysBack = 14

    fun component(): JComponent = panel {
        group("Timeline") {
            row { button("← Back") { navigator.showChooser() } }

            group("Function context") {
                row("Selected function:") { label("calculatePrice(Order)").bold() }
                row { comment("Build a timeline of commits affecting this function.") }
            }
            lateinit var TimeField: Cell<JBTextField>

            group("Parameters") {
                row("Days back:") {
                    TimeField=intTextField(1..365)
                        .bindIntText(
                            getter = { daysBack },
                            setter = { daysBack = it }
                        )
                }
            }

            row {
                button("Build timeline") {
                    val req = ActionRequest.Timeline(
                        daysBack = TimeField.component.text.toInt(),
                    )
                    actions.onActionSelected(req)
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}