package com.github.mattque04.hakatonproba.summary_generator
import com.github.mattque04.hakatonproba.cli.Cli

fun generate(projectPath: String, name: String, commitCount: Int): String {
    val cli = Cli(projectPath)

    val grepCommand = arrayOf(
        "git", "grep", "--break", "--heading",
        "--function-context", "--recurse-submodules",
        "--threads", "4", name, "HEAD"
    )

    val grepOutput = cli.runCommand(*grepCommand)
    if (grepOutput.isEmpty()) return "No matches found for '$name'."

    val matchedFiles = grepOutput.lines()
        .filter { it.startsWith("HEAD:") }
        .map { it.removePrefix("HEAD:").trim() }
        .filter { it.isNotEmpty() }

    if (matchedFiles.isEmpty()) return "No files matched for '$name'."

    val results = StringBuilder()
    val hash = "HEAD~$commitCount"
    for (file in matchedFiles) {
        val diffCommand = arrayOf(
            "git", "diff", "--find-renames", "--diff-filter=d",
            hash, "--", file
        )
        val diffOutput = cli.runCommand(*diffCommand)
        if (diffOutput.isNotEmpty()) {
            results.appendLine("\nFile: $file\n$diffOutput\n")
        }
    }

    return results.toString()
}