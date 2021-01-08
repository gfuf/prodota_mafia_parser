package com.gfuf.prodota.mafia.storage.dao.impl;

import com.gfuf.prodota.mafia.storage.dao.MafiaGameDAO;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MafiaGameCacheDAO implements MafiaGameDAO
{
    private final MafiaGameDAO mafiaGameDAO;

    private final Map<String, Boolean> isGameMap = new ConcurrentHashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public MafiaGameCacheDAO(MafiaGameDAO mafiaGameDAO)
    {
        this.mafiaGameDAO = mafiaGameDAO;
    }

    @PostConstruct
    public void init()
    {
        isGameMap.putAll(mafiaGameDAO.getAll());
    }

    @Override
    public boolean setIsGameTopic(String url, boolean isGame)
    {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try
        {
            boolean success = mafiaGameDAO.setIsGameTopic(url, isGame);
            if(success)
            {
                isGameMap.put(url, isGame);
            }
            return success;
        }
        finally
        {
            writeLock.unlock();
        }

    }

    @Override
    public Optional<Boolean> isGameTopic(String url)
    {
        return Optional.ofNullable(isGameMap.get(url));
    }

    @Override
    public Map<String, Boolean> getAll()
    {
        return new HashMap<>(isGameMap);
    }


}
