package gfuf.prodota.mafia.manager;

import gfuf.prodota.data.MafiaTopic;
import gfuf.prodota.data.Topic;

import java.util.Collection;
import java.util.Optional;

public interface MafiaManager
{
    Optional<MafiaTopic>  searchLastGameTopic();

    Collection<MafiaTopic> searchAllGameTopic();
}
