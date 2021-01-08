package com.gfuf.prodota.config;

import com.gfuf.prodota.mafia.manager.MafiaManager;
import com.gfuf.prodota.mafia.manager.impl.MafiaSimpleManager;
import com.gfuf.prodota.mafia.storage.dao.impl.MafiaCacheSingleRecordDAO;
import com.gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import com.gfuf.prodota.mafia.storage.service.MafiaStorageService;
import com.gfuf.prodota.mafia.storage.service.impl.MafiaSimpleStorageService;
import com.gfuf.prodota.mafia.task.GameSearchScheduller;
import com.gfuf.prodota.mafia.utils.IsGame;
import com.gfuf.prodota.parser.section.SectionParser;
import com.gfuf.prodota.parser.topic.TopicParser;
import com.gfuf.telegram.bot.AnouncerBot;
import com.gfuf.web.rest.RestWrapper;
import com.gfuf.prodota.mafia.storage.dao.MafiaDAO;
import com.gfuf.prodota.mafia.storage.dao.MafiaGameDAO;
import com.gfuf.prodota.mafia.storage.dao.impl.MafiaGameCacheDAO;
import com.gfuf.prodota.mafia.storage.service.impl.MafiaIsGameCustomizableSimpleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

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
    public IsGame isGame(MafiaIsGameCustomizableService mafiaIsGameCustomService)
    {
        return new IsGame(mafiaIsGameCustomService);
    }

    @Bean
    public MafiaIsGameCustomizableService mafiaIsGameCustomService(MafiaGameDAO mafiaGameCacheDAO)
    {
        return new MafiaIsGameCustomizableSimpleService(mafiaGameCacheDAO);
    }

    @Bean
    public MafiaGameDAO mafiaGameCacheDAO(MafiaGameDAO mafiaGamePostgresDAO)
    {
        return new MafiaGameCacheDAO(mafiaGamePostgresDAO);
    }



    @Bean
    public MafiaManager mafiaManager(RestWrapper restWrapper,
                                     SectionParser sectionParser,
                                     TopicParser topicParser,
                                     IsGame isGame,
                                     @Value("${prodota.mafia.start-page.url}") String prodotaMafiaStartPage)
    {
        return new MafiaSimpleManager(restWrapper, sectionParser, topicParser, isGame, URI.create(prodotaMafiaStartPage));
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
