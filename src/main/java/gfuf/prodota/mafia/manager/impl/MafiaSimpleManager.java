package gfuf.prodota.mafia.manager.impl;

import gfuf.prodota.data.Topic;
import gfuf.prodota.data.content.SectionContent;
import gfuf.prodota.mafia.manager.MafiaManager;
import gfuf.prodota.parser.Parser;
import gfuf.utils.ProdotaRuntimeException;
import gfuf.web.response.ResponseDecorator;
import gfuf.web.rest.RestWrapper;
import org.apache.commons.collections4.CollectionUtils;

import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MafiaSimpleManager implements MafiaManager
{
    private static final URI PRODOTA_MAFIA_START_PAGE_URI = URI.create("https://prodota.ru/forum/45/");

    private final RestWrapper restWrapper;

    private final Parser parser;

    private final Predicate<Topic> isGame;

    public MafiaSimpleManager(RestWrapper restWrapper, Parser parser, Predicate<Topic> isGame)
    {
        this.restWrapper = restWrapper;
        this.parser = parser;
        this.isGame = isGame;
    }

    @Override
    public Optional<Topic> searchLastGameTopic()
    {
        Optional<URI> uri = Optional.of(PRODOTA_MAFIA_START_PAGE_URI);
        Optional<Topic> result = Optional.empty();
        do {
            String page = getPage(uri.get());
            SectionContent sectionContent = parser.parse(page);
            result = searchLastGameTopic(sectionContent);
            uri = sectionContent.next();
        } while (result.isEmpty() && uri.isPresent());

        return result;
    }

    @Override
    public Collection<Topic> searchAllGameTopic()
    {
        Optional<URI> uri = Optional.of(PRODOTA_MAFIA_START_PAGE_URI);
        List<Topic> result = new ArrayList<>();
        do{
            String page = getPage(uri.get());
            SectionContent sectionContent = parser.parse(page);
            result.addAll(searchAllGameTopic(sectionContent));
            uri = sectionContent.next();
        }while (uri.isPresent());

        return result;
    }

    private Optional<Topic> searchLastGameTopic(SectionContent sectionContent)
    {
        return sectionContent.topics().stream().filter(isGame::test).findFirst();
    }

    private Collection<Topic> searchAllGameTopic(SectionContent sectionContent)
    {
        return sectionContent.topics().stream().filter(isGame::test).collect(Collectors.toList());
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
