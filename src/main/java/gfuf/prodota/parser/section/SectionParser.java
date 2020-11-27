package gfuf.prodota.parser.section;

import gfuf.prodota.data.topic.TopicStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import gfuf.prodota.data.Section;
import gfuf.prodota.data.topic.Topic;
import gfuf.prodota.data.content.SectionContent;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static gfuf.prodota.parser.section.SectionParserUtils.*;

public class SectionParser
{
    public SectionContent parse(String body)
    {
        Document doc = Jsoup.parse(body);

        return parse(doc);
    }

    public Collection<Section> parseSection(String body)
    {
        Document doc = Jsoup.parse(body);

        return parseSection(doc);
    }

    public Collection<Topic> parseTopics(String body)
    {
        Document doc = Jsoup.parse(body);

        return parseTopics(doc);
    }

    private SectionContent parse(Document doc)
    {
        Elements ipsData = doc.select(OL_IPS_DATA_LIST);

        String paginationNext = parsePaginationNext(doc);
        Collection<Section> sections = parseSection(doc);
        Collection<Topic> topics = parseTopics(doc);

        return SectionContent.of(sections, topics, Optional.ofNullable(paginationNext));
    }

    private String parsePaginationNext(Document doc)
    {
        Element paginationNextA = doc.selectFirst(PATH_TO_PAGINATION_NEXT_A);
        return paginationNextA != null ? paginationNextA.attr(HREF) : null;
    }


    private Collection<Section> parseSection(Document doc)
    {

        List<Section> sectionList = new ArrayList<>();
        Elements eSections = doc.select(PATH_TO_SECTION_LI);
        for (Element eSection : eSections)
        {
            Element sectionA = eSection.selectFirst(PATH_TO_SECTION_A);
            String name = sectionA.text();
            String href = sectionA.attr(HREF);
            URI uri = URI.create(href);

            Section section = new Section(name, uri);
            sectionList.add(section);
        }

        return sectionList;
    }


    private Collection<Topic> parseTopics(Document doc)
    {

        List<Topic> topicList = new ArrayList<>();
        Elements eTopics = doc.select(PATH_TO_TOPIC_LI);
        for (Element eTopic : eTopics)
        {
            Element sectionA = eTopic.selectFirst(PATH_TO_TOPIC_A);
            String name = sectionA.text();
            String href = sectionA.attr(HREF);
            URI uri = URI.create(href);
            Element sectionLock = eTopic.selectFirst(PATH_TO_FA_LOCK);
            TopicStatus status = parseTopicStatus(sectionLock);


            Topic topic = Topic.builder().setName(name)
                    .setUri(uri)
                    .setStatus(status)
                    .build();


            topicList.add(topic);
        }

        return topicList;
    }

    private TopicStatus parseTopicStatus(Element element)
    {
        return element == null ? TopicStatus.OPEN : TopicStatus.CLOSED;
    }


}
