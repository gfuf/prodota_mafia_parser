package com.gfuf.prodota.mafia.utils;

import com.gfuf.prodota.data.topic.Topic;
import com.gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class IsGame implements Predicate<Topic>
{
    private static final String GAME_PATTERN_STRING = "\\b(мафия|mafia)\\b[# №]*\\d+";

    private static final String REGISTRATION_PATTERN_STRING = "\\bрегистрация\\b";

    private static final Pattern GAME_PATTERN = Pattern.compile(GAME_PATTERN_STRING);

    private static final Pattern REGISTRATION_PATTERN = Pattern.compile(REGISTRATION_PATTERN_STRING);

    private final MafiaIsGameCustomizableService mafiaIsGameCustomService;

    public IsGame(MafiaIsGameCustomizableService mafiaIsGameCustomService)
    {
        this.mafiaIsGameCustomService = mafiaIsGameCustomService;
    }

    @Override
    public boolean test(Topic topic)
    {
        Optional<Boolean> isGame = mafiaIsGameCustomService.isGameTopic(topic.getUri().toString());
        boolean game = false;
        /*
        если вручную задали что по этому урлу игра, то берём эту информацию,
        иначе пытаемся определить по названию
        */
        if(isGame.isPresent())
        {
            game =  isGame.get();
        }
        else
        {
            String nameTopicLower = topic.getName().toLowerCase();
            game = GAME_PATTERN.matcher(nameTopicLower).find() && !REGISTRATION_PATTERN.matcher(nameTopicLower).find();
        }

        return game;
    }
}
