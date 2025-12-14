package com.github.mattque04.hakatonproba.openai

import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.responses.ResponseCreateParams

class OpenAi {
    private val apiKey: String = System.getenv("OPENAI_API_KEY")
        ?: throw IllegalStateException("Missing OPENAI_API_KEY environment variable")
    var client = OpenAIOkHttpClient.Companion.builder()
        .apiKey(apiKey)
        .build();

    fun call(instructions: String, input: String, model: ChatModel, temperature: Double? = 1.0, previousResponseId: String?): OpenAiResponse {
        val params = ResponseCreateParams.builder()
            .instructions(instructions)
            .input(input)
            .model(model)
            .temperature(temperature)
            .previousResponseId(previousResponseId)
            .build()

        val response = client.responses().create(params);

        return OpenAiResponse(
            response.output().last().message().get().content().get(0).outputText().get().text(),
            response.id().toString(),
        );
    }

    data class OpenAiResponse(val result: String, val responseId: String) {}

}