package web.rest;

import web.response.ResponseDecorator;

import java.net.URI;

public interface RestWrapper
{
    <T> ResponseDecorator<T> doGet(URI uri);

    default <T> ResponseDecorator<T> doGet(String url)
    {
       return doGet(URI.create(url));
    }
}
