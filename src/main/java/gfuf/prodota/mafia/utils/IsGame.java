package gfuf.prodota.mafia.utils;

import gfuf.prodota.data.Topic;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class IsGame implements Predicate<Topic>
{
    private static final String GAME_PATTERN_STRING = "\\b(мафия|mafia)\\b[# №]*\\d+";

    private static final Pattern GAME_PATTERN = Pattern.compile(GAME_PATTERN_STRING);

    @Override
    public boolean test(Topic topic)
    {
        String nameTopicLower = topic.getName().toLowerCase();
        return GAME_PATTERN.matcher(nameTopicLower).find();
    }
}
