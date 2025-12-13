package com.github.mattque04.hakatonproba.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtProperty
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiField


fun getPsiAtCaret(e: AnActionEvent): String? {
    val project = e.project ?: return null
    val editor = e.getData(CommonDataKeys.EDITOR) ?: return null
    val psiFile = e.getData(CommonDataKeys.PSI_FILE) ?: return null

    // 1️⃣ Uzimamo poziciju kursora (caret offset)
    val offset = editor.caretModel.offset

    // 2️⃣ Pronalazimo PSI element na toj poziciji
    val elementAtCaret = psiFile.findElementAt(offset) ?: return null

    // 3️⃣ Tražimo najbliži PSI roditelj koji je klasa, funkcija ili promenljiva
    val element = PsiTreeUtil.getParentOfType(
        elementAtCaret,
        PsiMethod::class.java,
        PsiClass::class.java,
        PsiField::class.java
    ) ?: return null

     //4️⃣ Uzimamo ime (ili FQN) elementa
    val elementName = when (element) {
        is KtNamedFunction -> element.fqName?.asString() ?: element.name ?: "<anonymous>"
        is KtClass -> element.fqName?.asString() ?: element.name ?: "<anonymous>"
        is KtProperty -> element.name ?: "<anonymous>"
        else -> "<unknown>"
    }

    // 5️⃣ Uzimamo relativnu putanju fajla
    val filePath = psiFile.virtualFile.path
    val projectPath = project.basePath ?: ""
    val relativePath = filePath.removePrefix(projectPath).removePrefix("/")

    // 6️⃣ Uzimamo ceo tekst tog PSI elementa
    val elementText = element.text.trim()

    // 7️⃣ Formatiramo rezultat
    return """
        Name: $elementName
        Path: $relativePath

        Code:
        -------------------------
        $elementText
    """.trimIndent()
    return ""
}


class ShowSelectedPsiAction : AnAction("Show Selected PSI") {
    override fun actionPerformed(e: AnActionEvent) {
        val description = getPsiAtCaret(e)
        if (description != null) {
            Messages.showInfoMessage(
                e.project,
                description,
                "Selected PSI Element"
            )
        } else {
            Messages.showWarningDialog(
                e.project,
                "No PSI element (function/class/property) was detected in your selection.",
                "No Element Found"
            )
        }
    }
}