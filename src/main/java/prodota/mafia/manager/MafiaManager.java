package prodota.mafia.manager;

import prodota.data.Topic;

import java.util.Optional;

public interface MafiaManager
{
    Optional<Topic>  searchLastGameTopic();
}
