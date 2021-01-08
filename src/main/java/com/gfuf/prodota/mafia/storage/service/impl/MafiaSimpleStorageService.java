package com.gfuf.prodota.mafia.storage.service.impl;

import com.gfuf.prodota.data.topic.MafiaTopic;
import com.gfuf.prodota.mafia.storage.service.MafiaStorageService;
import com.gfuf.prodota.mafia.storage.dao.MafiaDAO;

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

    @Override
    public boolean deleteTopicByUrl(String url)
    {
        return mafiaDAO.deleteTopicByUrl(url);
    }


}
