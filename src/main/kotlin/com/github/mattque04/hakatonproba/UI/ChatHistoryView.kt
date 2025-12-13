package com.github.mattque04.hakatonproba.UI


import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import javax.swing.JComponent

class ChatHistoryView {

    private val history = JBTextArea().apply {
        isEditable = false
        lineWrap = true
        wrapStyleWord = true
    }

    fun component(): JComponent = JBScrollPane(history)

    fun append(text: String) {
        history.append(text)
        history.caretPosition = history.document.length
    }
}