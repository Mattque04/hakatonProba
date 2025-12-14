package com.github.mattque04.hakatonproba.core
import java.io.BufferedReader
import java.io.InputStreamReader

object GitUtils {

    fun getAllBranches(projectPath: String): List<String> {
        return try {
            val process = ProcessBuilder("git", "branch", "-a")
                .directory(java.io.File(projectPath))
                .redirectErrorStream(true)
                .start()

            val output = BufferedReader(InputStreamReader(process.inputStream)).readLines()
            process.waitFor()

            output.map { it.trim().removePrefix("* ").trim() }  // skida zvezdicu sa aktivne grane
        } catch (e: Exception) {
            listOf("Error: ${e.message}")
        }
    }
}