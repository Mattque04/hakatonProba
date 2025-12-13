package com.github.mattque04.hakatonproba.UI



class ControllerImpl(
    private val navigator: UiNavigator
) : MainActions, ChatActions {

    override fun onActionSelected(actionName: String) {
        navigator.showChat()
        // kasnije: pokreni Git/OpenAI posao
    }

    override fun onSendMessage(text: String) {
        // kasnije: obrada poruke
    }
}