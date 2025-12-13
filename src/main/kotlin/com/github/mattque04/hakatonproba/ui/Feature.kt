package com.github.mattque04.hakatonproba.ui

enum class Feature(val label: String, val description: String) {
    SUMMARIZE(
        "Summarize changes for function",
        "AI summary of changes affecting the selected function in the chosen time range."
    ),
    COMPARE(
        "Compare last N commits",
        "Compare how the selected function changed across the last N commits."
    ),
    TIMELINE(
        "Show timeline (last N days)",
        "Build a time-ordered view of commits that affected the selected function."
    );

    override fun toString(): String = label
}