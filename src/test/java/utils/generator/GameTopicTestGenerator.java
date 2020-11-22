package utils.generator;

import gfuf.prodota.data.Topic;
import gfuf.prodota.mafia.manager.MafiaManager;
import gfuf.prodota.mafia.manager.impl.MafiaSimpleManager;
import gfuf.prodota.mafia.utils.IsGame;
import gfuf.prodota.parser.section.SectionParser;
import gfuf.prodota.parser.topic.TopicParser;
import gfuf.web.rest.RestWrapper;
import gfuf.web.rest.impl.RestSimpleWrapper;

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
        IsGame isGame = new IsGame();
        SectionParser sectionParser = new SectionParser();
        TopicParser topicParser = new TopicParser();
        MafiaManager mafiaManager = new MafiaSimpleManager(restWrapper, sectionParser, topicParser, isGame);
        Collection<Topic> topics = mafiaManager.searchAllGameTopic();
        Files.createDirectories(resourcePath("generated"));
        Files.writeString(resourcePath("generated","game_topics.txt"), topics.stream().map(Topic::getName).collect(Collectors.joining("\n")));
    }


}
