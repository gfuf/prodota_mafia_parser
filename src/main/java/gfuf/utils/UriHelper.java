package gfuf.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class UriHelper
{
    public static URI createWithoutParameters(String url)
    {
        try
        {

            URI uri = new URI(url);
            return new URI(uri.getScheme(),
                    uri.getAuthority(),
                    uri.getPath(),
                    null,
                    null);
        }
        catch (URISyntaxException x)
        {
            throw new IllegalArgumentException(x.getMessage(), x);
        }
    }
}
