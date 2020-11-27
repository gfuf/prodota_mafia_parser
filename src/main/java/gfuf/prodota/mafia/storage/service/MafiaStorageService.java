package gfuf.prodota.mafia.storage.service;

import gfuf.prodota.data.topic.MafiaTopic;

import java.util.Optional;

public interface MafiaStorageService
{
    Optional<MafiaTopic> lastTopic();

    Optional<MafiaTopic> topicByUrl(String url);

    boolean writeTopic(MafiaTopic topic);
}
