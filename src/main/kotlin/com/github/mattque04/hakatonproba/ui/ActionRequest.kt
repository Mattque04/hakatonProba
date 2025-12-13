package com.github.mattque04.hakatonproba.ui

import com.intellij.psi.PsiElement

sealed class ActionRequest {
    data class Summarize(val element: PsiElement, val daysBack: Int, val maxCommits: Int) : ActionRequest()
    data class Compare(val nCommits: Int) : ActionRequest()
    data class Timeline(val daysBack: Int) : ActionRequest()
}