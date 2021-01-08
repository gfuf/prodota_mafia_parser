package com.gfuf.prodota.mafia.task;

import com.gfuf.prodota.data.topic.MafiaTopic;
import com.gfuf.prodota.mafia.storage.service.MafiaStorageService;
import com.gfuf.telegram.bot.AnouncerBot;
import org.springframework.scheduling.annotation.Scheduled;
import com.gfuf.prodota.mafia.manager.MafiaManager;

import java.util.Objects;
import java.util.Optional;

public class GameSearchScheduller
{
    private final MafiaManager mafiaManager;

    private final MafiaStorageService mafiaStorageService;

    private final AnouncerBot anouncerBot;

    public GameSearchScheduller(MafiaManager mafiaManager,
                                MafiaStorageService mafiaStorageService,
                                AnouncerBot anouncerBot)
    {
        this.mafiaManager = mafiaManager;
        this.mafiaStorageService = mafiaStorageService;
        this.anouncerBot = anouncerBot;

    }

    @Scheduled(fixedDelayString = "${prodota.mafia.task.delay}")
    public void search()
    {
        Optional<MafiaTopic> lastGameTopic = mafiaManager.searchLastGameTopic();


        if(lastGameTopic.isPresent())
        {
            Optional<MafiaTopic> topicFromCache = mafiaStorageService.topicByUrl(lastGameTopic.get().getUri().toString());
            //такого топика ещё не было
            if(topicFromCache.isEmpty())
            {
                boolean success = anouncerBot.sendToAnouncerChat(lastGameTopic.get());
                if(success)
                {
                    mafiaStorageService.writeTopic(lastGameTopic.get());
                }
            }
            //проверка на изменения в топике
            else if(!Objects.equals(lastGameTopic, topicFromCache))
            {
                boolean success = anouncerBot.editMessage(lastGameTopic.get());
                if(success)
                {
                    mafiaStorageService.writeTopic(lastGameTopic.get());
                }
            }
        }
    }
}
