package com.gfuf.prodota.data.content;

import org.apache.commons.collections4.CollectionUtils;
import com.gfuf.prodota.data.Section;
import com.gfuf.prodota.data.topic.Topic;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public record SectionContent(Collection<Section> sections, Collection<Topic> topics, Optional<URI> next)
{
    public static SectionContent of(Collection<Section> sections, Collection<Topic> topics, Optional<String> next)
    {
        return new SectionContent(sections, topics, next.map(URI::create));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionContent content = (SectionContent) o;
        return CollectionUtils.isEqualCollection(sections, content.sections) &&
                CollectionUtils.isEqualCollection(topics, content.topics) &&
                Objects.equals(next, content.next);
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
}