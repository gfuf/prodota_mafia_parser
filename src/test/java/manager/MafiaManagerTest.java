package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prodota.data.Topic;
import prodota.mafia.manager.MafiaManager;
import prodota.mafia.manager.impl.MafiaSimpleManager;
import prodota.mafia.utils.IsGame;
import prodota.parser.Parser;
import web.response.ResponseDecorator;
import web.rest.RestWrapper;

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
        Parser parser = new Parser();

        mafiaManager = new MafiaSimpleManager(restWrapper, parser, isGame);

    }

    @Test
    public void testSearchLastGameTopic() throws IOException
    {
        Topic testTopic = new Topic("Мафия 444. Трудно быть демоном. Ночи в 21-00", URI.create("https://prodota.ru/forum/topic/219541/?do=getNewComment"));
        Optional<Topic> topic = mafiaManager.searchLastGameTopic();

        assertTrue(topic.isPresent());
        assertEquals(topic.get(), testTopic);
    }
}
