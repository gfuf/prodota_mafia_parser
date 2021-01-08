package com.gfuf.prodota.data.topic;

import com.gfuf.prodota.data.IpsData;

import java.net.URI;
import java.util.Objects;

public abstract class AbstractTopic  implements IpsData
{
    private final String name;

    private final URI uri;

    private final TopicStatus status;

    public AbstractTopic(AbstractBuilder abstractBuilder)
    {
        this.name = abstractBuilder.getName();
        this.uri = abstractBuilder.getUri();
        this.status = abstractBuilder.getStatus();
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTopic topic = (AbstractTopic) o;
        return name.equals(topic.name) &&
                uri.equals(topic.uri) &&
                status == topic.status;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, uri, status);
    }


    public static abstract class AbstractBuilder<T extends AbstractBuilder>
    {
        private String name;

        private URI uri;

        private TopicStatus status;

        public AbstractBuilder from(AbstractTopic topic)
        {
            setName(topic.getName())
                    .setUri(topic.getUri())
                    .setStatus(topic.getStatus());
            return getThis();
        }

        private String getName()
        {
            return name;
        }

        public T setName(String name)
        {
            this.name = name;
            return getThis();
        }

        private URI getUri()
        {
            return uri;
        }

        public T setUri(URI uri)
        {
            this.uri = uri;
            return getThis();
        }

        private TopicStatus getStatus()
        {
            return status;
        }

        public T setStatus(TopicStatus status)
        {
            this.status = status;
            return getThis();
        }

        public abstract T getThis();
    }
}
