package com.github.mattque04.hakatonproba.summary_generator
import com.github.mattque04.hakatonproba.openai.OpenAi
import com.github.mattque04.hakatonproba.cli.Cli
import com.github.mattque04.hakatonproba.openai.OpenAi.OpenAiResponse

class RebaseSummaryGenerator(var openAi: OpenAi) {
    fun generate(projectPath: String, branch: String = "main"): OpenAiResponse? {
        val cli = Cli(projectPath)

        val diffCommand = arrayOf("git", "diff", "--function-context", branch)
        val diffOutput = cli.runCommand(*diffCommand)

        if (diffOutput.isEmpty()) {
            return null
        }

        val prompt = """
Here are differences of current branch and another branch: '$diffOutput'. 
User wants to rebase against main. 
Can you give the user heads up of the most significant differences present? 
Furthermore, if the differences are huge, abstract and summerise them in few sentances.
        """.trimIndent()

        return this.openAi.call(
            "",
            prompt,
            com.openai.models.ChatModel.GPT_5,
            null,
            null
        )

    }
}