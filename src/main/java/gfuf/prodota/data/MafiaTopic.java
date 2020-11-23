package gfuf.prodota.data;

import java.util.Objects;
import java.util.Optional;

public class MafiaTopic extends AbstractTopic
{
    private final Optional<String> pictureUrl;

    public MafiaTopic(Builder builder)
    {
        super(builder);
        this.pictureUrl = builder.getPictureUrl();
    }

    public Optional<String> getPictureUrl()
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
        private Optional<String> pictureUrl;

        public Builder from(Topic topic)
        {
            super.from(topic);
            return getThis();
        }


        public Builder setPictureUrl(Optional<String> pictureUrl)
        {
            this.pictureUrl = pictureUrl;
            return getThis();
        }

        private Optional<String> getPictureUrl()
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
