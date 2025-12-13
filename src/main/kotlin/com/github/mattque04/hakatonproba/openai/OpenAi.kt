package com.github.mattque04.hakatonproba.openai

import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.responses.ResponseCreateParams

class OpenAi {

    var client = OpenAIOkHttpClient.Companion.builder()
        .apiKey("")
        .build();

    fun call(instructions: String, input: String, model: ChatModel, temperature: Double? = 1.0): String {
        val params = ResponseCreateParams.Companion.builder()
            .instructions(instructions)
            .input(input)
            .model(model)
            .temperature(temperature)
            .build()

        val response = client.responses().create(params);

        return response.output().last().message().get().content().get(0).outputText().get().text();
    }

}