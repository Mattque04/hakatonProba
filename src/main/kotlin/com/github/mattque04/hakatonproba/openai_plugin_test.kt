package com.github.mattque04.hakatonproba

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.responses.ResponseCreateParams



class openai_plugin_test : AnAction("Show Action") {

    var client = OpenAIOkHttpClient.builder()
        .apiKey("pusite kurac nema api key za dzaba")
        .build();


    override fun actionPerformed(e: AnActionEvent) {

        val params = ResponseCreateParams.builder()
            .input("Say this is a test")
            .model(ChatModel.GPT_4_1)
            .build()

        val response = client.responses().create(params);

        Messages.showInfoMessage(response.output().get(0).message().get().content().get(0).outputText().get().text(), "Title");

    }
}