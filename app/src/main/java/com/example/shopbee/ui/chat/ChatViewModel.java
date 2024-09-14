package com.example.shopbee.ui.chat;

import com.example.shopbee.BuildConfig;
import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.google.ai.client.generativeai.Chat;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.ChatFutures;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatViewModel extends BaseViewModel<ChatNavigator> {
    GenerativeModel gm;
    GenerativeModelFutures model;
    ChatFutures chat;
    public ChatViewModel(Repository repository) {
        super(repository);
        Content.Builder userContentBuilder = new Content.Builder();
        userContentBuilder.setRole("user");
        userContentBuilder.addText("You are the assistant of the Application called Shopbee, which is an online e-commerce platform like Shopee or Amazon. The authors of this application are Pham Minh Anh, Nguyen Minh Luan, and Nguyen Manh Dinh, students of University of Science. You have to talk with the user of this application, please inform them about the app.");
        Content.Builder userContentBuilder2 = new Content.Builder();
        userContentBuilder2.setRole("user");
        userContentBuilder2.addText("Your name is Bee Assistant. You should reply the answer more like a human and more concise than a bot. Encouraging the user to user the application and buy as much as possible");
        Content.Builder modelContentBuilder = new Content.Builder();
        modelContentBuilder.setRole("model");
        modelContentBuilder.addText("👋 Hi there! Thanks for checking out Shopbee! 🎉 I'm your friendly Shopbee assistant, here to help you find amazing deals and discover awesome products. 😊 Shopbee is a brand new online marketplace built with ❤️ by students at University of Science – Pham Minh Anh, Nguyen Minh Luan, and Nguyen Manh Dinh. They're passionate about connecting buyers and sellers in a fun, easy, and secure way. What can I help you with today? Here are some things I can do: * Tell you about Shopbee's features (like secure payment options, buyer/seller protections, and our awesome product categories!) * Help you find specific products (just tell me what you're looking for!) * Answer questions about your account (like how to track your orders or contact a seller). Just let me know what's on your mind! 😄");
        List<Content> chatHistory = Arrays.asList(userContentBuilder.build(), userContentBuilder2.build(), modelContentBuilder.build());
        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        configBuilder.temperature = 1f;
        configBuilder.topK = 32;
        configBuilder.topP = 1f;
        configBuilder.maxOutputTokens = 4096;

        ArrayList<SafetySetting> safetySettings = new ArrayList();
        safetySettings.add(new SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE));
        safetySettings.add(new SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE));
        safetySettings.add(new SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE));
        safetySettings.add(new SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE));

        gm = new GenerativeModel(
                "gemini-1.5-flash",
                BuildConfig.apiKey,
                configBuilder.build(),
                safetySettings
        );
        model = GenerativeModelFutures.from(gm);
        chat = model.startChat(chatHistory);
    }
    Boolean first;
    void sendMessageWithStreamResponse(String message) {
        Content.Builder contentBuilder = new Content.Builder();
        contentBuilder.setRole("user");
        contentBuilder.addText(message);
        Content content = contentBuilder.build();
        Publisher<GenerateContentResponse> streamingResponse = chat.sendMessageStream(content);
        first = true;
        streamingResponse.subscribe(new Subscriber<GenerateContentResponse>() {
            @Override
            public void onNext(GenerateContentResponse generateContentResponse) {
                String chunk = generateContentResponse.getText();
                if (first) {
                    getNavigator().onResponse(chunk);
                    first = false;
                }
                else getNavigator().onStreamingResponse(chunk);
            }

            @Override
            public void onComplete() {
                // ...
            }

            @Override
            public void onError(Throwable t) {
                getNavigator().onResponse(t.getMessage());
            }

            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);  // Request all messages
            }
        });
    }
    void sendMessage(String message) {
        Content.Builder contentBuilder = new Content.Builder();
        contentBuilder.setRole("user");
        contentBuilder.addText(message);
        Content content = contentBuilder.build();
        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = chat.sendMessage(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String answer = result.getText();
                        getNavigator().onResponse(answer);
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        getNavigator().onResponse(t.getMessage());
                    }
                },
                executor
        );
    }
}
