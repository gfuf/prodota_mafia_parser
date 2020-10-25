package prodota.data;

import java.net.URI;

public record Section(String name, URI uri) implements IpsData
{
    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public URI getUri()
    {
        return uri;
    }
}
