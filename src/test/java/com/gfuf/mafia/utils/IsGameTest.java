package com.gfuf.mafia.utils;

import com.gfuf.prodota.data.topic.Topic;
import com.gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import com.gfuf.prodota.mafia.utils.IsGame;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static utils.TestUtils.resourcePath;

import static org.mockito.Mockito.*;

public class IsGameTest
{
    private IsGame isGame;

    @Before
    public void before()
    {
        MafiaIsGameCustomizableService mafiaIsGameCustomService = mock(MafiaIsGameCustomizableService.class);
        when(mafiaIsGameCustomService.isGameTopic(anyString())).thenReturn(Optional.empty());

        isGame = new IsGame(mafiaIsGameCustomService);
    }

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
        when(topic.getUri()).thenReturn(URI.create("https://prodota.ru"));

        return topic;
    }


}
