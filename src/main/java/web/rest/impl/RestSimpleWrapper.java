package web.rest.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.response.ResponseDecorator;
import web.rest.RestWrapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

//TODO написать полноценный класс
public class RestSimpleWrapper implements RestWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(RestSimpleWrapper.class);

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36";

    private final HttpClient httpClient;

    public RestSimpleWrapper()
    {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public ResponseDecorator<String> doGet(URI uri)
    {
        HttpRequest request = buildGetRequest(uri);

        HttpResponse<String> response = null;
        Exception exception = null;
        try
        {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception e)
        {
           logger.error("Error while get uri = {} ", uri, e);
           exception = e;
        }

        return new ResponseDecorator<String>(response.body(), exception);
    }

    private HttpRequest buildGetRequest(URI uri)
    {
        return HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .setHeader("User-Agent", USER_AGENT) // TODO add request header
                .build();
    }
}
