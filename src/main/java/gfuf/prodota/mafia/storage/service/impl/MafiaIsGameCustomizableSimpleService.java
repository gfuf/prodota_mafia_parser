package gfuf.prodota.mafia.storage.service.impl;

import gfuf.prodota.mafia.storage.dao.MafiaGameDAO;
import gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;

import java.util.Map;
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
