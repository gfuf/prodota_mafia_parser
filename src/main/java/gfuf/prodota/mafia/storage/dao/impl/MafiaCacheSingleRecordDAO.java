package gfuf.prodota.mafia.storage.dao.impl;

import gfuf.prodota.data.Topic;
import gfuf.prodota.mafia.storage.dao.MafiaDAO;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

public class MafiaCacheSingleRecordDAO implements MafiaDAO
{
    private Optional<Topic> currentTopic;

    private final MafiaDAO mafiaDAO;

    public MafiaCacheSingleRecordDAO(MafiaDAO mafiaDAO)
    {
        this.mafiaDAO = mafiaDAO;
    }

    @PostConstruct
    public void init()
    {
        this.currentTopic = mafiaDAO.lastTopic();
    }


    @Override
    public Optional<Topic> lastTopic()
    {
        return currentTopic;
    }

    @Override
    public Optional<Topic> topicByUrl(String url)
    {
        return currentTopic
                .filter(t -> Objects.equals(t.getUri().toString(), url))
                .isPresent()  ? currentTopic : mafiaDAO.topicByUrl(url);
    }

    @Override
    public boolean writeTopic(Topic topic)
    {
        boolean success = mafiaDAO.writeTopic(topic);
        if (success)
        {
            this.currentTopic = Optional.of(topic);
        }

        return  success;
    }
}
