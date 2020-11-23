package gfuf.prodota.data;

import java.net.URI;
import java.util.Objects;

public class Topic extends AbstractTopic
{
    public Topic(Builder builder)
    {
        super(builder);
    }

    public static Builder builder()
    {
        return new Builder();
    }


    public static class Builder extends AbstractTopic.AbstractBuilder<Builder>
    {
        @Override
        public Builder getThis()
        {
            return this;
        }

        public Topic build()
        {
            return new Topic(getThis());
        }
    }
}
