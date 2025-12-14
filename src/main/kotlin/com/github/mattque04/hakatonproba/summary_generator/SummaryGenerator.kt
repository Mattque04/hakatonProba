package com.github.mattque04.hakatonproba.summary_generator
import com.github.mattque04.hakatonproba.cli.Cli
import com.github.mattque04.hakatonproba.openai.OpenAi
import com.github.mattque04.hakatonproba.openai.OpenAi.OpenAiResponse

class SummaryGenerator(var openAi: OpenAi) {

    fun generate(projectPath: String, filePath: String, name: String, commitCount: Int): OpenAiResponse? {
        val cli = Cli(projectPath)

        val grepCommand = arrayOf(
            "git", "grep", "--break", "--heading",
            "--function-context", "--recurse-submodules",
            "--threads", "4", name, "HEAD"
        )

        val grepOutput = cli.runCommand(*grepCommand)
        if (grepOutput.isEmpty()) return null

        val matchedFiles = grepOutput.lines()
            .filter { it.startsWith("HEAD:") }
            .map { it.removePrefix("HEAD:").trim() }
            .filter { it.isNotEmpty() }

        if (matchedFiles.isEmpty()) return null

        val diffs = StringBuilder()
        val hash = "HEAD~$commitCount"
        for (file in matchedFiles) {
            val diffCommand = arrayOf(
                "git", "diff", "--find-renames", "--diff-filter=d",
                hash, "--", file
            )
            val diffOutput = cli.runCommand(*diffCommand)
            if (diffOutput.isNotEmpty()) {
                diffs.appendLine("\nFile: $file\n$diffOutput\n")
            }
        }


        var prompt = """
You are presented with formated git grep output of this class/function:
$name

Here are uses and definitions:
$grepOutput

Here are differences that occured in last $commitCount commits;
for each file in ${matchedFiles.joinToString("\n")}
 $diffs

Summerise for the user most important changes that occured in the definition and the uses of this function in the last ${'$'}{ N } commits. Write it concise, commenting all aspects of argument submission and intricasies of function/class definition.
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


