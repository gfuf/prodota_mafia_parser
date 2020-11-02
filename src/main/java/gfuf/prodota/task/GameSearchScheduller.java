package gfuf.prodota.task;

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
        Optional<Topic> oldLastGameTopic = mafiaStorageService.findLastGameTopic();

        if(lastGameTopic.isPresent())
        {
            if(!lastGameTopic.equals(oldLastGameTopic))
            {
                System.out.println(lastGameTopic.get());
                mafiaStorageService.writeLastGameTopic(lastGameTopic.get());
            }
            else
            {
                System.out.println("Equals"); //TODO видимо ничего делать не надо, возможно обновить данные
            }
        }


    }
}
