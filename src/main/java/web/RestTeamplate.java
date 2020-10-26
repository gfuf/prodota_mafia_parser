package web;

import java.net.URI;

public interface RestTeamplate
{
    String doGet(URI uri);

    default String doGet(String url)
    {
       return doGet(URI.create(url));
    }
}
