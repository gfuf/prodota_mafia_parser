package gfuf.prodota.mafia.manager;

import gfuf.prodota.data.topic.MafiaTopic;

import java.util.Collection;
import java.util.Optional;

public interface MafiaManager
{
    Optional<MafiaTopic>  searchLastGameTopic();

    Collection<MafiaTopic> searchAllGameTopic();
}
