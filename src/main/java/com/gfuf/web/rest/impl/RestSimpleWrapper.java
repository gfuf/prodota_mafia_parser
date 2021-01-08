package com.gfuf.web.rest.impl;

import com.gfuf.web.response.ResponseDecorator;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gfuf.web.rest.RestWrapper;

import javax.ws.rs.core.HttpHeaders;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

public class RestSimpleWrapper implements RestWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(RestSimpleWrapper.class.getCanonicalName());

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
        try
        {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (Exception e)
        {
           logger.error("Error while get uri = {} ", uri, e);
           return new ResponseDecorator<String>(e);
        }

        //если 301 код ошибки, пытаемся редеректнуться на другой урл
        if(response.statusCode() == HttpStatus.SC_MOVED_PERMANENTLY)
        {
            Optional<String> location = response.headers().firstValue(HttpHeaders.LOCATION);
            if(location.isPresent())
            {
                return doGet(URI.create(location.get()));
            }
        }

        return new ResponseDecorator<String>(response.body());
    }

    private HttpRequest buildGetRequest(URI uri)
    {
        return HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .setHeader("User-Agent", USER_AGENT)
                .build();
    }
}