package gfuf.prodota.mafia.storage.dao;

import gfuf.prodota.data.Topic;

import java.util.Optional;

public interface MafiaDAO
{
    Optional<Topic> lastTopic();

    Optional<Topic> topicByUrl(String url);

    boolean writeTopic(Topic topic);

}
