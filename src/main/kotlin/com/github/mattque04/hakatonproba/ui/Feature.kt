package com.github.mattque04.hakatonproba.ui

enum class Feature(val label: String, val description: String) {
    SUMMARIZE(
        "Summarize changes for function",
        "AI summary of changes affecting the selected function in the chosen time range."
    ),
    COMPARE(
        "Compare current branch with choosen branch",
        "Show how the current branch can be in conflict with choosen branch."
    ),
    SUMMARIZE_COMMIT(
        "Summarize changes for choosen branch",
        "Summarize changes for choosen branch has been reached."
    );

    override fun toString(): String = label
}