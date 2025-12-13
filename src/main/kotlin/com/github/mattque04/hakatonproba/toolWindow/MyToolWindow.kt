package com.github.mattque04.hakatonproba.toolWindow

import com.github.mattque04.hakatonproba.UI.ToolWindowRoot
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.components.JBPanel
import java.awt.BorderLayout

class MyToolWindow(toolWindow: ToolWindow) {

    fun getContent() = JBPanel<JBPanel<*>>().apply {
        layout = BorderLayout()
        add(ToolWindowRoot().component(), BorderLayout.CENTER)
    }
}