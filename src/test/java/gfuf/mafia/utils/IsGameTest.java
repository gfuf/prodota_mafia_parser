package gfuf.mafia.utils;

import gfuf.prodota.data.topic.Topic;
import gfuf.prodota.mafia.utils.IsGame;
import org.junit.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static utils.TestUtils.resourcePath;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

public class IsGameTest
{
    //TODO
    private IsGame isGame = new IsGame(null);

    @Test
    public void testByGenerated() throws IOException
    {
       List<String> generatedGameNames = Files.readAllLines(resourcePath("generated","game_topics.txt"));

       generatedGameNames.stream()
               .map(IsGameTest::toTopic)
               .forEach(game -> assertTrue(isGame.test(game)));
    }

    private static Topic toTopic(String name)
    {

        Topic topic = mock(Topic.class);
        when(topic.getName()).thenReturn(name);

        return topic;
    }


}
