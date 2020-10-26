import prodota.mafia.manager.MafiaManager;
import prodota.mafia.manager.impl.MafiaSimpleManager;
import prodota.mafia.util.IsGame;
import prodota.parser.Parser;
import web.RestTeamplate;
import web.impl.RestSimpleTemplate;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        RestTeamplate restTeamplate = new RestSimpleTemplate();
        Parser parser = new Parser();
        IsGame isGame = new IsGame();
        MafiaManager mafiaManager = new MafiaSimpleManager(restTeamplate, parser, isGame);

        System.out.println(mafiaManager.searchLastGameTopic());

    }
}
