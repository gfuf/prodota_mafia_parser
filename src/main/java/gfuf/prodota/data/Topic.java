package gfuf.prodota.data;

import org.glassfish.jersey.server.Uri;

import java.net.URI;
import java.util.Objects;

public class Topic implements IpsData
{
    private final String name;

    private final URI uri;

    private final TopicStatus status;


    public Topic(String name, URI uri)
    {
        this(name, uri, TopicStatus.CLOSED);
    }

    public Topic(String name, URI uri, TopicStatus status)
    {
        this.name = name;
        this.uri = uri;
        this.status = status;
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

    public TopicStatus getStatus()
    {
        return status;
    }

    //TODO equals
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(name, topic.name) &&
                Objects.equals(uri, topic.uri);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, uri);
    }
}
