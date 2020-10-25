package prodota.mafia.manager.impl;

import prodota.data.Topic;
import prodota.mafia.manager.MafiaManager;
import prodota.parser.Parser;
import web.RestTeamplate;

public class MafiaSimpleManager implements MafiaManager
{
    private final RestTeamplate restTeamplate;

    private final Parser parser;

    public MafiaSimpleManager(RestTeamplate restTeamplate, Parser parser)
    {
        this.restTeamplate = restTeamplate;
        this.parser = parser;
    }

    @Override
    //TODO написать полноценный метод
    public Topic searchLastGameTopic()
    {

        return null;
    }
}
