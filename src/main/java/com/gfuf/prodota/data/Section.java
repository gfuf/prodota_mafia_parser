package com.gfuf.prodota.data;

import java.net.URI;
import java.util.Objects;

public final class Section implements IpsData
{
    private final String name;
    private final URI uri;

    public Section(String name, URI uri)
    {
        this.name = name;
        this.uri = uri;
    }

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

    public String name()
    {
        return name;
    }

    public URI uri()
    {
        return uri;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Section) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.uri, that.uri);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, uri);
    }

    @Override
    public String toString()
    {
        return "Section[" +
                "name=" + name + ", " +
                "uri=" + uri + ']';
    }

}
