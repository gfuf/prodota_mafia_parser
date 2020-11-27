package gfuf.prodota.config;

import gfuf.prodota.mafia.storage.dao.MafiaDAO;
import gfuf.prodota.mafia.storage.dao.impl.MafiaCacheSingleRecordDAO;
import gfuf.prodota.mafia.storage.service.MafiaStorageService;
import gfuf.prodota.mafia.storage.service.impl.MafiaSimpleStorageService;
import gfuf.prodota.parser.topic.TopicParser;
import gfuf.telegram.bot.AnouncerBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gfuf.prodota.parser.section.SectionParser;
import gfuf.prodota.mafia.manager.MafiaManager;
import gfuf.prodota.mafia.manager.impl.MafiaSimpleManager;
import gfuf.prodota.mafia.utils.IsGame;
import gfuf.prodota.mafia.task.GameSearchScheduller;
import gfuf.web.rest.RestWrapper;

@Configuration
public class ProdotaConfig
{
    @Bean
    public SectionParser sectionParser()
    {
        return new SectionParser();
    }

    @Bean
    public TopicParser topicParser()
    {
        return new TopicParser();
    }

    @Bean
    public IsGame isGame()
    {
        return new IsGame();
    }

    @Bean
    public MafiaManager mafiaManager(RestWrapper restWrapper,
                                     SectionParser sectionParser,
                                     TopicParser topicParser,
                                     IsGame isGame)
    {
        return new MafiaSimpleManager(restWrapper, sectionParser, topicParser, isGame);
    }

    @Bean
    public GameSearchScheduller gameSearchScheduller(MafiaManager mafiaManager,
                                                     MafiaStorageService mafiaStorageService,
                                                     AnouncerBot anouncerBot)
    {
        return new GameSearchScheduller(mafiaManager, mafiaStorageService, anouncerBot);
    }

    @Bean
    public MafiaStorageService mafiaService(MafiaDAO mafiaCacheSingleRecordDAO)
    {
        return new MafiaSimpleStorageService(mafiaCacheSingleRecordDAO);
    }

    @Bean
    public MafiaCacheSingleRecordDAO mafiaCacheSingleRecordDAO(MafiaDAO mafiaPostgresDAO)
    {
        return new MafiaCacheSingleRecordDAO(mafiaPostgresDAO);
    }
}
