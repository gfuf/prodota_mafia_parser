package prodota.data.content;

import org.apache.commons.collections4.CollectionUtils;
import prodota.data.Section;
import prodota.data.Topic;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record SectionContent (Collection<Section> sections, Collection<Topic> topics)
{
    public static SectionContent of(Collection<Section> sections, Collection<Topic> topics)
    {
        return new SectionContent(sections, topics);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionContent content = (SectionContent) o;
        return CollectionUtils.isEqualCollection(sections, content.sections) &&
                CollectionUtils.isEqualCollection(topics, content.topics);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
