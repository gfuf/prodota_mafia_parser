package gfuf.prodota.config;

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
    public GameSearchScheduller gameSearchScheduller(MafiaManager mafiaManager)
    {
        return new GameSearchScheduller(mafiaManager);
    }

}
