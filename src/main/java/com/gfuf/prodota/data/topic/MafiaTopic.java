package com.gfuf.prodota.data.topic;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class MafiaTopic extends AbstractTopic
{
    private final Optional<URI> pictureUrl;

    public MafiaTopic(Builder builder)
    {
        super(builder);
        this.pictureUrl = builder.getPictureUrl();
    }

    public Optional<URI> getPictureUrl()
    {
        return pictureUrl;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MafiaTopic that = (MafiaTopic) o;
        return Objects.equals(pictureUrl, that.pictureUrl);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), pictureUrl);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder extends Topic.AbstractBuilder<Builder>
    {
        private Optional<URI> pictureUrl;

        public Builder from(Topic topic)
        {
            super.from(topic);
            return getThis();
        }


        public Builder setPictureUrl(Optional<URI> pictureUrl)
        {
            this.pictureUrl = pictureUrl;
            return getThis();
        }

        private Optional<URI> getPictureUrl()
        {
            return pictureUrl;
        }

        public Builder getThis()
        {
            return this;
        }

        public MafiaTopic build()
        {
            return new MafiaTopic(getThis());
        }

    }
}
