package gfuf.prodota.mafia.storage.dao.impl;

import gfuf.prodota.data.Topic;
import gfuf.prodota.mafia.storage.dao.MafiaDAO;

import java.util.*;

public class MafiaPostgresDAO implements MafiaDAO
{
    private final Stack<Topic> topics = new Stack<>();
    @Override
    public Optional<Topic> findLastGameTopic()
    {
        return topics.isEmpty() ? Optional.empty() : Optional.ofNullable(topics.peek());
    }

    @Override
    public boolean writeLastGameTopic(Topic topic)
    {
        boolean contains = topics.contains(topic);

        if(!contains)
        {
            topics.push(topic);
        }

        return contains;
    }
}
