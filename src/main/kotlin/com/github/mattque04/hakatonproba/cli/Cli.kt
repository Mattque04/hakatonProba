package com.github.mattque04.hakatonproba.cli

import java.io.File

class Cli {

    private var path: String = "";

    constructor(path: String) {
        this.path = path
    }

    fun runCommand(vararg command: String): String {
        val process = ProcessBuilder(command.toList())
            .directory(File(path))
            .redirectErrorStream(true)
            .start()

        val exitCode = process.waitFor()

        return process.inputStream.bufferedReader().readText()
    }

}