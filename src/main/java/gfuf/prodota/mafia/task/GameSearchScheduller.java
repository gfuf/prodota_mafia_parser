package gfuf.prodota.mafia.task;

import gfuf.prodota.data.Topic;
import gfuf.prodota.mafia.storage.service.MafiaStorageService;
import org.springframework.scheduling.annotation.Scheduled;
import gfuf.prodota.mafia.manager.MafiaManager;

import java.util.Optional;

public class GameSearchScheduller
{
    private final MafiaManager mafiaManager;

    private final MafiaStorageService mafiaStorageService;

    public GameSearchScheduller(MafiaManager mafiaManager, MafiaStorageService mafiaStorageService)
    {
        this.mafiaManager = mafiaManager;
        this.mafiaStorageService = mafiaStorageService;
    }

    @Scheduled(fixedDelay = 5000)
    public void search()
    {
        Optional<Topic> lastGameTopic = mafiaManager.searchLastGameTopic();


        if(lastGameTopic.isPresent())
        {
            Optional<Topic> topicFromCache = mafiaStorageService.topicByUrl(lastGameTopic.get().getUri().toString());
            //такого топика ещё не было
            if(topicFromCache.isEmpty())
            {
                mafiaStorageService.writeTopic(lastGameTopic.get());
                System.out.println(lastGameTopic.get());
            }
            else
            {
                System.out.println("Equals"); //TODO видимо ничего делать не надо, возможно обновить данные
            }
        }


    }
}
