package com.gfuf.prodota.mafia.storage.dao;

import java.util.Map;
import java.util.Optional;

/*
 * dao для ассоциации урла топик с игрой
 */
public interface MafiaGameDAO
{
    boolean setIsGameTopic(String url, boolean isGameTopic);

    Optional<Boolean> isGameTopic(String url);

    Map<String, Boolean> getAll();
}
