package com.github.mattque04.hakatonproba.ui

import com.github.mattque04.hakatonproba.openai.OpenAi
import com.github.mattque04.hakatonproba.summary_generator.CommitSummaryGenerator
import com.github.mattque04.hakatonproba.summary_generator.SummaryGenerator
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.ProjectManager
import com.intellij.psi.PsiElement
import java.awt.event.ActionEvent

class ControllerImpl(
    private val navigator: UiNavigator,
    private val chatView: ChatView
) : MainActions, ChatActions {

    override fun onActionSelected(request: ActionRequest) {
        when (request) {
            is ActionRequest.Summarize -> runSummarize(request.element, request.daysBack, request.maxCommits)
            is ActionRequest.Compare -> runCompare(request.targetBranch)
            is ActionRequest.Timeline -> runTimeline(request.commitId)
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

        chatView.clear()
        chatView.append(summary!!.result)
        chatView.responseId = summary.responseId
        navigator.showChat()
    }

    private fun runCompare(targetBranch: String) {
        chatView.append("System: Compare started (targetBranch=$targetBranch)\n\n")
        navigator.showChat()
    }

    private fun runTimeline(commitId: String) {
        var summarized =
            CommitSummaryGenerator(OpenAi()).generate(
                ProjectManager.getInstance().openProjects.firstOrNull()!!.basePath!!,
            commitId,
        )
        chatView.append(summarized!!.result)
        navigator.showChat()
    }
}