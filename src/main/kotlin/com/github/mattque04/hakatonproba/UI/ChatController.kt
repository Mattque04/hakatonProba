package com.github.mattque04.hakatonproba.ui

interface ChatController {
    fun onActionSelected(actionName: String)
    fun onSendMessage(text: String)
}