package com.gfuf.prodota.mafia.storage.service.impl;

import com.gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import com.gfuf.prodota.mafia.storage.dao.MafiaGameDAO;

import java.util.Optional;

public class MafiaIsGameCustomizableSimpleService implements MafiaIsGameCustomizableService
{
    private final MafiaGameDAO mafiaGameDAO;

    public MafiaIsGameCustomizableSimpleService(MafiaGameDAO mafiaGameDAO)
    {
        this.mafiaGameDAO = mafiaGameDAO;
    }


    @Override
    public boolean setIsGameTopic(String url, boolean isGame)
    {
        return mafiaGameDAO.setIsGameTopic(url, isGame);
    }

    @Override
    public Optional<Boolean> isGameTopic(String url)
    {
        return mafiaGameDAO.isGameTopic(url);
    }
}
