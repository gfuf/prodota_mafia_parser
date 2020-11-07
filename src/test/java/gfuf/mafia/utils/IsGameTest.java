package gfuf.mafia.utils;

import gfuf.prodota.data.Topic;
import gfuf.prodota.mafia.utils.IsGame;
import org.hamcrest.core.Is;
import org.junit.Test;


import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

import static utils.TestUtils.resourcePath;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsGameTest
{
    private IsGame isGame = new IsGame();

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
        return new Topic(name, URI.create(""));
    }


}
