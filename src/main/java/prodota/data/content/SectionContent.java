package prodota.data.content;

import prodota.data.Section;
import prodota.data.Topic;

import java.util.Collection;
import java.util.List;

public record SectionContent (Collection<Section> sections, Collection<Topic> topics)
{
    public static SectionContent of(Collection<Section> sections, Collection<Topic> topics)
    {
        return new SectionContent(sections, topics);
    }
}
