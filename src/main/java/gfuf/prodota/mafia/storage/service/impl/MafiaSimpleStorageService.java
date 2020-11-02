package gfuf.prodota.mafia.storage.service.impl;

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
    public Optional<Topic> findLastGameTopic()
    {
        return mafiaDAO.findLastGameTopic();
    }

    @Override
    public boolean writeLastGameTopic(Topic topic)
    {
        return mafiaDAO.writeLastGameTopic(topic);
    }


}
