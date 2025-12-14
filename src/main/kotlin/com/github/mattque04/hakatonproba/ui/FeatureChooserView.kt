package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent
import javax.swing.JComboBox
import javax.swing.JLabel

class FeatureChooserView(
    private val navigator: UiNavigator
) {
    private val featureBox = JComboBox(arrayOf("Summarize", "Potentional conficts", "Summarize commit"))
    private val descLabel = JLabel("AI tools for understanding code")

    fun component(): JComponent = panel {
        group("Git Summary Assistant") {
            row("Feature:") { cell(featureBox).align(AlignX.FILL) }
            row("Description:") { cell(descLabel).align(AlignX.FILL) }

            row {
                button("Open") {
                    when (featureBox.selectedItem as String) {
                        "Summarize" -> navigator.showSummarize()
                        "Potentional conficts" -> navigator.showCompare()
                        "Summarize commit" -> navigator.showTimeline()
                    }
                }.align(AlignX.FILL)
            }
        }
    }.also {
        featureBox.addActionListener {
            descLabel.text = when (featureBox.selectedItem as String) {
                "Summarize" -> "Follow element changes through commits"
                "Potentional conficts" -> "Compare potential conflicts current branch with chosen branch."
                else -> "Summarize changes for choosen branch has been reached."
            }
        }
    }
}