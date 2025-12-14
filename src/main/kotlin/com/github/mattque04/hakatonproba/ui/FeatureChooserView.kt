package com.github.mattque04.hakatonproba.ui

import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent
import javax.swing.JComboBox
import javax.swing.JLabel

class FeatureChooserView(
    private val navigator: UiNavigator
) {
    private val featureBox = JComboBox(arrayOf("Summarize", "Compare", "Summarize commit"))
    private val descLabel = JLabel("AI summary of changes affecting the selected function in a time range.")

    fun component(): JComponent = panel {
        group("Git Summary Assistant") {
            row("Feature:") { cell(featureBox).align(AlignX.FILL) }
            row("Description:") { cell(descLabel).align(AlignX.FILL) }

            row {
                button("Open") {
                    when (featureBox.selectedItem as String) {
                        "Summarize" -> navigator.showSummarize()
                        "Compare" -> navigator.showCompare()
                        "Summarize commit" -> navigator.showTimeline()
                    }
                }.align(AlignX.FILL)
            }
        }
    }.also {
        featureBox.addActionListener {
            descLabel.text = when (featureBox.selectedItem as String) {
                "Summarize" -> "AI summary of changes affecting the selected function in a time range."
                "Compare" -> "Compare how the selected function changed across last N commits."
                else -> "Timeline view of commits touching the selected function in last N days."
            }
        }
    }
}