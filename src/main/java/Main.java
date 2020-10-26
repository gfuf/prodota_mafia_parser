import prodota.mafia.manager.MafiaManager;
import prodota.mafia.manager.impl.MafiaSimpleManager;
import prodota.mafia.utils.IsGame;
import prodota.parser.Parser;
import web.rest.RestWrapper;
import web.rest.impl.RestSimpleWrapper;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        RestWrapper restWrapper = new RestSimpleWrapper();
        Parser parser = new Parser();
        IsGame isGame = new IsGame();
        MafiaManager mafiaManager = new MafiaSimpleManager(restWrapper, parser, isGame);

        System.out.println(mafiaManager.searchLastGameTopic());

    }
}
