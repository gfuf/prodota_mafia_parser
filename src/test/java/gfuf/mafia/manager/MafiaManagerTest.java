package gfuf.mafia.manager;

import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.prodota.data.topic.TopicStatus;
import gfuf.prodota.parser.topic.TopicParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gfuf.prodota.mafia.manager.MafiaManager;
import gfuf.prodota.mafia.manager.impl.MafiaSimpleManager;
import gfuf.prodota.mafia.utils.IsGame;
import gfuf.prodota.parser.section.SectionParser;
import gfuf.web.response.ResponseDecorator;
import gfuf.web.rest.RestWrapper;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static utils.TestUtils.resourcePath;

public class MafiaManagerTest
{
    private MafiaManager mafiaManager;

    @BeforeEach
    public void before() throws IOException
    {
        RestWrapper restWrapper = mock(RestWrapper.class);
        String page = Files.readString(resourcePath("prodota_forum_mafia.html"));
        when(restWrapper.doGet(any(URI.class))).thenReturn(new ResponseDecorator<>(page));

        IsGame isGame = new IsGame();
        SectionParser sectionParser = new SectionParser();
        TopicParser topicParser = new TopicParser();

        mafiaManager = new MafiaSimpleManager(restWrapper, sectionParser, topicParser, isGame, URI.create("https://prodota.ru/forum/45/"));

    }

    @Test
    public void testSearchLastGameTopic() throws IOException
    {
        MafiaTopic testTopic = mock(MafiaTopic.class);
        when(testTopic.getUri()).thenReturn(URI.create("https://prodota.ru/forum/topic/219541/?do=getNewComment"));
        when(testTopic.getName()).thenReturn("Мафия 444. Трудно быть демоном. Ночи в 21-00");
        when(testTopic.getStatus()).thenReturn(TopicStatus.CLOSED);
        when(testTopic.getPictureUrl()).thenReturn(Optional.ofNullable(URI.create("https://prodota.ru/forum/topic/219541/?do=getNewComment")));

        Optional<MafiaTopic> topic = mafiaManager.searchLastGameTopic();

        assertTrue(topic.isPresent());
        assertEquals(topic.get().getUri(), testTopic.getUri());
    }
}
