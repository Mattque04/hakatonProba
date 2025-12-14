package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent
import javax.swing.JLabel
import kotlin.Int

class SummarizeFeatureView(
    private val navigator: UiNavigator,
    private val actions: MainActions,
    private  var functionLabel: JLabel? = null
) {
    var daysBack = 30
    var maxCommits = 50
    fun component(): JComponent = panel {
        group("Summarize") {
            row { button("← Back") { navigator.showChooser() } }

            group("Function context") {
                row("Selected function:") { functionLabel = label("not selected").component }
                row { comment("Pick a function in the editor, then run summary.") }
            }

            lateinit var daysField: Cell<JBTextField>
            lateinit var maxCommitsField: Cell<JBTextField>

            group("Parameters") {
                row("Days back:") {
                    daysField = intTextField(7..365)
                        .bindIntText(
                            getter = { daysBack },
                            setter = { daysBack = it }
                        )
                }
                row("Max commits:") {
                    maxCommitsField = intTextField(1..200)
                        .bindIntText(
                            getter = {
                                maxCommits
                                     },
                            setter = {
                                maxCommits = it
                            }
                        )
                }
            }



            row {
                button("Run summary") {
                    val req = ActionRequest.Summarize(
                        GlobalVariables.selectedElement!!,
                        daysBack =  daysField.component.text.toInt(),
                        maxCommits = maxCommitsField.component.text.toInt(),
                    )
                    actions.onActionSelected(req)
                    navigator.showChat()
                }.align(AlignX.FILL)
            }.topGap(TopGap.MEDIUM)
        }
    }

    public fun setFunctionLabel(text: String) {
        functionLabel!!.text = text
    }

}