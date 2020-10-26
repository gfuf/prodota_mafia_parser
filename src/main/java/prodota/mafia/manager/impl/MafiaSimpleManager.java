package prodota.mafia.manager.impl;

import prodota.data.Topic;
import prodota.data.content.SectionContent;
import prodota.mafia.manager.MafiaManager;
import prodota.parser.Parser;
import utils.ProdotaRuntimeException;
import web.response.ResponseDecorator;
import web.rest.RestWrapper;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.Optional;
import java.util.function.Predicate;

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

    private Optional<Topic> searchLastGameTopic(SectionContent sectionContent)
    {
        return sectionContent.topics().stream().filter(isGame::test).findFirst();
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
