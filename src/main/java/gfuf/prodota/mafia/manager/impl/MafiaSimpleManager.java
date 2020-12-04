package gfuf.prodota.mafia.manager.impl;

import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.prodota.data.topic.Topic;
import gfuf.prodota.data.content.SectionContent;
import gfuf.prodota.mafia.manager.MafiaManager;
import gfuf.prodota.parser.section.SectionParser;
import gfuf.prodota.parser.topic.TopicParser;
import gfuf.utils.ProdotaRuntimeException;
import gfuf.web.response.ResponseDecorator;
import gfuf.web.rest.RestWrapper;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MafiaSimpleManager implements MafiaManager
{
    private final RestWrapper restWrapper;

    private final SectionParser sectionParser;

    private final TopicParser topicParser;

    private final Predicate<Topic> isGame;

    private final URI prodotaMafiaStartPageUri;

    public MafiaSimpleManager(RestWrapper restWrapper,
                              SectionParser sectionParser,
                              TopicParser topicParser,
                              Predicate<Topic> isGame,
                              URI prodotaMafiaStartPageUri)
    {
        this.restWrapper = restWrapper;
        this.sectionParser = sectionParser;
        this.topicParser = topicParser;
        this.isGame = isGame;
        this.prodotaMafiaStartPageUri = prodotaMafiaStartPageUri;
    }

    @Override
    public Optional<MafiaTopic> searchLastGameTopic()
    {
        Optional<URI> uri = Optional.of(prodotaMafiaStartPageUri);
        Optional<MafiaTopic> result = Optional.empty();
        do {
            String page = getPage(uri.get());
            SectionContent sectionContent = sectionParser.parse(page);
            result = searchLastGameTopic(sectionContent);
            uri = sectionContent.next();
        } while (result.isEmpty() && uri.isPresent());

        return result;
    }

    @Override
    public Collection<MafiaTopic> searchAllGameTopic()
    {
        Optional<URI> uri = Optional.of(prodotaMafiaStartPageUri);
        List<MafiaTopic> result = new ArrayList<>();
        do{
            String page = getPage(uri.get());
            SectionContent sectionContent = sectionParser.parse(page);
            result.addAll(searchAllGameTopic(sectionContent));
            uri = sectionContent.next();
        }while (uri.isPresent());

        return result;
    }


    private Optional<MafiaTopic> searchLastGameTopic(SectionContent sectionContent)
    {
        return sectionContent.topics().stream().filter(isGame::test)
                .map(this::toMafiaTopic).findFirst();
    }

    private Collection<MafiaTopic> searchAllGameTopic(SectionContent sectionContent)
    {
        return sectionContent.topics().stream().filter(isGame::test)
                .map(this::toMafiaTopic).collect(Collectors.toList());
    }

    private MafiaTopic toMafiaTopic(Topic topic)
    {
        Optional<URI> pictureUri = loadTopicFirstPicture(topic).map(URI::create);
        return MafiaTopic.builder().from(topic).setPictureUrl(pictureUri).build();
    }


    private Optional<String> loadTopicFirstPicture(Topic topic)
    {
        return loadTopicFirstPicture(topic.getUri());
    }

    private Optional<String> loadTopicFirstPicture(URI uri)
    {
        String page = getPage(uri);
        return topicParser.findFirstPicture(page);
    }

    private String getPage(URI uri)
    {
        ResponseDecorator<String> response = restWrapper.doGet(uri);
        if(response.hasResponse())
        {
            return response.getResult();
        }
        else
        {
            throw new ProdotaRuntimeException("Can't get from "+ uri +" , while search last game topic", response.getError());
        }
    }
}
