package utils.generator;

import com.gfuf.prodota.data.topic.MafiaTopic;
import com.gfuf.prodota.mafia.manager.MafiaManager;
import com.gfuf.prodota.mafia.manager.impl.MafiaSimpleManager;
import com.gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import com.gfuf.prodota.mafia.utils.IsGame;
import com.gfuf.prodota.parser.section.SectionParser;
import com.gfuf.prodota.parser.topic.TopicParser;
import com.gfuf.web.rest.RestWrapper;
import com.gfuf.web.rest.impl.RestSimpleWrapper;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static utils.TestUtils.resourcePath;

public class GameTopicTestGenerator
{
    private static final URI PRODOTA_MAFIA_START_PAGE_URI = URI.create("https://prodota.ru/forum/45/");

    public static void main(String[] args) throws IOException
    {
        RestWrapper restWrapper = new RestSimpleWrapper();
        IsGame isGame = new IsGame(new EmptyMafiaIsGameCustomService());
        SectionParser sectionParser = new SectionParser();
        TopicParser topicParser = new TopicParser();
        MafiaManager mafiaManager = new MafiaSimpleManager(restWrapper, sectionParser, topicParser, isGame, URI.create("https://prodota.ru/forum/45/"));
        Collection<MafiaTopic> topics = mafiaManager.searchAllGameTopic();
        Files.createDirectories(resourcePath("generated"));
        Files.writeString(resourcePath("generated","game_topics.txt"), topics.stream().map(MafiaTopic::getName).collect(Collectors.joining("\n")));
    }

    private static class EmptyMafiaIsGameCustomService implements MafiaIsGameCustomizableService
    {

        @Override
        public boolean setIsGameTopic(String url, boolean isGameTopic)
        {
            return false;
        }

        @Override
        public Optional<Boolean> isGameTopic(String url)
        {
            return Optional.empty();
        }
    }


}
