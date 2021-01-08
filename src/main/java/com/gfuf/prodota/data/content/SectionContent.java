package com.gfuf.prodota.data.content;

import org.apache.commons.collections4.CollectionUtils;
import com.gfuf.prodota.data.Section;
import com.gfuf.prodota.data.topic.Topic;

import java.net.URI;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public final class SectionContent
{
    private final Collection<Section> sections;
    private final Collection<Topic> topics;
    private final Optional<URI> next;

    public SectionContent(Collection<Section> sections, Collection<Topic> topics, Optional<URI> next)
    {
        this.sections = sections;
        this.topics = topics;
        this.next = next;
    }

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

    public Collection<Section> sections()
    {
        return sections;
    }

    public Collection<Topic> topics()
    {
        return topics;
    }

    public Optional<URI> next()
    {
        return next;
    }

    @Override
    public String toString()
    {
        return "SectionContent[" +
                "sections=" + sections + ", " +
                "topics=" + topics + ", " +
                "next=" + next + ']';
    }

}
