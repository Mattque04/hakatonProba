package com.github.mattque04.hakatonproba.ui

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
    var nCommits = 20

    fun component(): JComponent = panel {
        group("Compare") {
            row { button("← Back") { navigator.showChooser() } }

            group("Function context") {
                row("Selected function:") { label("calculatePrice(Order)").bold() }
                row { comment("Compare changes to this function across commits.") }
            }
            lateinit var ComitsField: Cell<JBTextField>
            group("Parameters") {
                row("N commits:") {
                    ComitsField= intTextField(1..200)
                        .bindIntText(
                            getter = { nCommits },
                            setter = { nCommits = it }
                        )
                }
            }

            row {
                button("Compare") {
                    val req = ActionRequest.Compare(
                        nCommits =  ComitsField.component.text.toInt(),

                        )
                    actions.onActionSelected(req)
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }
}