package gfuf.prodota.mafia.storage.service.impl;

import gfuf.prodota.data.MafiaTopic;
import gfuf.prodota.data.Topic;
import gfuf.prodota.mafia.storage.dao.MafiaDAO;
import gfuf.prodota.mafia.storage.service.MafiaStorageService;

import java.util.Optional;


public class MafiaSimpleStorageService implements MafiaStorageService
{
    private final MafiaDAO mafiaDAO;

    public MafiaSimpleStorageService(MafiaDAO mafiaDAO)
    {
        this.mafiaDAO = mafiaDAO;
    }

    @Override
    public Optional<MafiaTopic> lastTopic()
    {
        return mafiaDAO.lastTopic();
    }

    @Override
    public Optional<MafiaTopic> topicByUrl(String url)
    {
        return mafiaDAO.topicByUrl(url);
    }

    @Override
    public boolean writeTopic(MafiaTopic topic)
    {
        return mafiaDAO.writeTopic(topic);
    }


}
