package gfuf.prodota.config;

import gfuf.prodota.mafia.storage.dao.MafiaDAO;
import gfuf.prodota.mafia.storage.dao.impl.MafiaPostgresDAO;
import gfuf.prodota.mafia.storage.service.MafiaStorageService;
import gfuf.prodota.mafia.storage.service.impl.MafiaSimpleStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import gfuf.prodota.parser.Parser;
import gfuf.prodota.mafia.manager.MafiaManager;
import gfuf.prodota.mafia.manager.impl.MafiaSimpleManager;
import gfuf.prodota.mafia.utils.IsGame;
import gfuf.prodota.task.GameSearchScheduller;
import gfuf.web.rest.RestWrapper;

@Configuration
public class ProdotaConfig
{
    @Bean
    public Parser parser()
    {
        return new Parser();
    }

    @Bean
    public IsGame isGame()
    {
        return new IsGame();
    }

    @Bean
    public MafiaManager mafiaManager(RestWrapper restWrapper, Parser parser, IsGame isGame)
    {
        return new MafiaSimpleManager(restWrapper, parser, isGame);
    }

    @Bean
    public GameSearchScheduller gameSearchScheduller(MafiaManager mafiaManager, MafiaStorageService mafiaStorageService)
    {
        return new GameSearchScheduller(mafiaManager, mafiaStorageService);
    }

    @Bean
    public MafiaStorageService mafiaService(MafiaDAO mafiaPostgresDAO)
    {
        return new MafiaSimpleStorageService(mafiaPostgresDAO);
    }

    @Bean
    public MafiaDAO mafiaPostgresDAO()
    {
        return new MafiaPostgresDAO();
    }

}
