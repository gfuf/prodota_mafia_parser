package gfuf.prodota.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import gfuf.prodota.mafia.manager.MafiaManager;

public class GameSearchScheduller
{
    private final MafiaManager mafiaManager;

    public GameSearchScheduller(MafiaManager mafiaManager)
    {
        this.mafiaManager = mafiaManager;
    }

    @Scheduled(fixedDelay = 5000)
    public void search()
    {
        System.out.println(mafiaManager.searchLastGameTopic());
    }
}
