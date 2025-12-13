package com.github.mattque04.hakatonproba.ui

import com.github.mattque04.hakatonproba.openai.OpenAi
import com.github.mattque04.hakatonproba.summary_generator.SummaryGenerator
import com.intellij.psi.PsiElement

class ControllerImpl(
    private val navigator: UiNavigator,
    private val chatView: ChatView
) : MainActions, ChatActions {

    override fun onActionSelected(request: ActionRequest) {
        when (request) {
            is ActionRequest.Summarize -> runSummarize(request.element, request.daysBack, request.maxCommits)
            is ActionRequest.Compare -> runCompare(request.nCommits)
            is ActionRequest.Timeline -> runTimeline(request.daysBack)
        }
    }

    override fun onSendMessage(text: String) {
        chatView.append("You: $text\n")
        chatView.append("Assistant: (placeholder response)\n\n")
    }

    private fun runSummarize(element: PsiElement, daysBack: Int, maxCommits: Int) {
        var summary = SummaryGenerator(OpenAi()).generate(
            element.project.basePath!!,
            element.containingFile.virtualFile.path,
            element.text,
            maxCommits
        )

        chatView.append(summary!!.result)
        navigator.showChat()
    }

    private fun runCompare(nCommits: Int) {
        chatView.append("System: Compare started (nCommits=$nCommits)\n\n")
        navigator.showChat()
    }

    private fun runTimeline(daysBack: Int) {
        chatView.append("System: Timeline started (daysBack=$daysBack)\n\n")
        navigator.showChat()
    }
}