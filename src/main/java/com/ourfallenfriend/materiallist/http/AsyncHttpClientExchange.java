package com.ourfallenfriend.materiallist.http;

import com.ourfallenfriend.materiallist.Contractor;
import com.ourfallenfriend.materiallist.MaterialList;
import com.ourfallenfriend.materiallist.messenger.BakedMessage;
import com.ourfallenfriend.materiallist.messenger.MessageType;
import com.ourfallenfriend.materiallist.messenger.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;

public class AsyncHttpClientExchange {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void doRequest(UUID responseReceiver, MaterialList materialList) throws IOException, InterruptedException, TimeoutException {
        // json data
        String json = materialList.toJson();

        // build request w/ json appended to body.
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create("http://localhost:3000/MaterialList"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(10))
                .build();

        // send response async, upon futurecompletion apply response body to accept lambda, calling for the completion of the contract.
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(url -> Contractor.getInstance().completeContract(responseReceiver, url))
                .whenComplete((res, ex) -> {
                    if(ex instanceof CompletionException || ex instanceof ConnectException) {
                        Player player = Bukkit.getPlayer(responseReceiver);
                        if(Objects.isNull(player)) return;

                        Messenger.getInstance().sendMessage(player, BakedMessage.SERVER_CONNECTION_FAILED);
                        Contractor.getInstance().voidContract(responseReceiver);
                    }
                });
    }
}
