package com.github.mattque04.hakatonproba.actions

import com.github.mattque04.hakatonproba.openai.OpenAi
import com.github.mattque04.hakatonproba.summary_generator.SummaryGenerator
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiVariable


fun getPsiAtCaret(e: AnActionEvent): PsiElement? {
    val project = e.project ?: return null
    val editor = e.getData(CommonDataKeys.EDITOR) ?: return null
    val psiFile = e.getData(CommonDataKeys.PSI_FILE) ?: return null

    val offset = editor.caretModel.offset

    val validElementTypes = listOf(
        PsiClass::class.java,
        PsiMethod::class.java,
        PsiField::class.java,
        PsiVariable::class.java
    )

    var element = psiFile.findElementAt(offset)

    if (!validElementTypes.any { it.isInstance(element!!.parent) })
        return null

    return element
}


class ShowSelectedPsiAction : AnAction("Show Selected PSI") {
    override fun actionPerformed(e: AnActionEvent) {
        val element = getPsiAtCaret(e)
        if (element != null) {

            var summary = SummaryGenerator(OpenAi()).generate(
                e.project!!.basePath!!,
                element.containingFile.virtualFile.path,
                element.text,
                3
            )

            Messages.showWarningDialog(
                e.project,
                summary,
                "Summary"
            )
        } else {
            Messages.showWarningDialog(
                e.project,
                "No PSI element (function/class/property/variable) was detected in your selection.",
                "No Element Found"
            )
        }
    }

    fun getRelativePath(projectPath: String, filePath: String): String? {
        return if (filePath.startsWith(projectPath)) {
            filePath.removePrefix(projectPath)
                .removePrefix("/")
                .removePrefix("\\")
        } else {
            null
        }
    }
}