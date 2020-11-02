package gfuf.prodota.mafia.storage.dao;

import gfuf.prodota.data.Topic;

import java.util.Optional;

public interface MafiaDAO
{
    Optional<Topic> findLastGameTopic();

    boolean writeLastGameTopic(Topic topic);
}
