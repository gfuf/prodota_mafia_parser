package com.gfuf.prodota.mafia.storage.service;

import java.util.Map;
import java.util.Optional;

public interface MafiaIsGameCustomizableService
{
    /*
      изменить isGame для урла
    */
    boolean setIsGameTopic(String url, boolean isGameTopic);

    /*
        если есть кастомное значение для данного урла вернёт его,
        если нет вернёт Optional.empty()
    */
    Optional<Boolean> isGameTopic(String url);
}
