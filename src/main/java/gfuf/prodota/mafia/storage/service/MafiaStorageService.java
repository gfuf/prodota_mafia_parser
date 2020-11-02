package gfuf.prodota.mafia.storage.service;

import gfuf.prodota.data.Topic;

import java.util.Optional;

public interface MafiaStorageService
{
    Optional<Topic> findLastGameTopic();

    boolean writeLastGameTopic(Topic topic);
}
