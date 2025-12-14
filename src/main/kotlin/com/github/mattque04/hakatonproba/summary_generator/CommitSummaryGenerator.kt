package com.github.mattque04.hakatonproba.summary_generator

import com.github.mattque04.hakatonproba.cli.Cli
import com.github.mattque04.hakatonproba.openai.OpenAi
import com.github.mattque04.hakatonproba.openai.OpenAi.OpenAiResponse

class CommitSummaryGenerator(var openAi: OpenAi) {
    fun generate(projectPath: String, hash_id: String): OpenAiResponse? {
        val cli = Cli(projectPath)

        val command = arrayOf("git", "show", "--diff-filter=drc", hash_id)
        val diffOutput = cli.runCommand(*command)

        if (diffOutput.isEmpty()) {
            return null
        }

        val prompt = """
You are analyzing a git commit diff.

Commit ID: $hash_id

Here is the full diff output (only code-related changes):
$diffOutput

Summarize this commit for the user.
- Highlight what files and functions were added, removed, or modified.
- Mention any significant refactors or design changes.
- Keep the summary concise but informative.
        """.trimIndent()

        return this.openAi.call(
            "",
            prompt,
            com.openai.models.ChatModel.GPT_4O_MINI,
            null,
            null
        )
    }
}