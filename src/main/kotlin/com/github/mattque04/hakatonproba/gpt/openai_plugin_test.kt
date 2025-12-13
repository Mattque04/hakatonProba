package com.github.mattque04.hakatonproba.gpt

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.responses.ResponseCreateParams

class openai_plugin_test : AnAction("Show Action") {

    var client = OpenAIOkHttpClient.Companion.builder()
        .apiKey("")
        .build();


    override fun actionPerformed(e: AnActionEvent) {

        val params = ResponseCreateParams.Companion.builder()
            .input("Say this is a test")
            .model(ChatModel.Companion.GPT_4_1)
            .build()

        val response = client.responses().create(params);

        Messages.showInfoMessage(response.output().get(0).message().get().content().get(0).outputText().get().text(), "Title");

    }
}