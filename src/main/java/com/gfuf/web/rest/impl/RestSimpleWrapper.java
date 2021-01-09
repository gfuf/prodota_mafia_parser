package com.gfuf.web.rest.impl;

import com.gfuf.web.response.ResponseDecorator;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gfuf.web.rest.RestWrapper;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.Optional;

public class RestSimpleWrapper implements RestWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(RestSimpleWrapper.class.getCanonicalName());

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36";

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    public ResponseDecorator<String> doGet(URI uri)
    {
        HttpGet httpGet = buildGetRequest(uri);

        HttpResponse response = null;
        try
        {
            response = httpClient.execute(httpGet);
        }
        catch (Exception e)
        {
           logger.error("Error while get uri = {} ", uri, e);
           return new ResponseDecorator<String>(e);
        }

        //если 301 код ошибки, пытаемся редеректнуться на другой урл
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY)
        {
            String location = response.getFirstHeader(HttpHeaders.LOCATION).getValue();
            if(location != null)
            {
                return doGet(URI.create(location));
            }
        }

        try
        {
            return new ResponseDecorator<String>(EntityUtils.toString(response.getEntity()));
        } catch (IOException e)
        {
            logger.error("Error while entity to string = {} ", response.getEntity(), e);
            return new ResponseDecorator<String>(e);
        }
    }

    private HttpGet buildGetRequest(URI uri)
    {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader("User-Agent", USER_AGENT);

        return httpGet;
    }
}
