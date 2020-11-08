package gfuf.prodota.mafia.storage.service;

import gfuf.prodota.data.Topic;

import java.util.Optional;

public interface MafiaStorageService
{
    Optional<Topic> lastTopic();

    Optional<Topic> topicByUrl(String url);

    boolean writeTopic(Topic topic);
}
