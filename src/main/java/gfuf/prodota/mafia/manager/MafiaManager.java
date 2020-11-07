package gfuf.prodota.mafia.manager;

import gfuf.prodota.data.Topic;

import java.util.Collection;
import java.util.Optional;

public interface MafiaManager
{
    Optional<Topic>  searchLastGameTopic();

    Collection<Topic> searchAllGameTopic();
}
