package web.impl;

import web.RestTeamplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

//TODO написать полноценный класс
public class RestSimpleTemplate implements RestTeamplate
{

    @Override
    public String doGet(URI uri)
    {
        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36") // add request header
                .build();
        HttpResponse<String> response = null;
        try
        {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return response != null ? response.body() : new String();
    }
}
