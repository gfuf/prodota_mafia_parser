package gfuf.prodota.mafia.storage.dao;

import gfuf.prodota.data.topic.MafiaTopic;

import java.util.Optional;

public interface MafiaDAO
{
    Optional<MafiaTopic> lastTopic();

    Optional<MafiaTopic> topicByUrl(String url);

    boolean writeTopic(MafiaTopic topic);

}
