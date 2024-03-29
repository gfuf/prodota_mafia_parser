package com.gfuf.prodota.mafia.storage.dao.impl;

import com.gfuf.prodota.mafia.storage.dao.MafiaGameDAO;
import com.gfuf.prodota.mafia.storage.mapper.IsGameMapper;
import com.gfuf.prodota.mafia.storage.mapper.IsGameUrlMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MafiaGamePostgresDAO extends JdbcDaoSupport implements MafiaGameDAO
{
    private static final IsGameMapper IS_GAME_MAPPER = new IsGameMapper();

    private static final IsGameUrlMapMapper IS_GAME_URL_MAP_MAPPER = new IsGameUrlMapMapper();

    @Autowired
    public MafiaGamePostgresDAO(DataSource dataSource)
    {
        this.setDataSource(dataSource);
    }

    @Override
    public boolean setIsGameTopic(String url, boolean isGameTopic)
    {
        int i = this.getJdbcTemplate().update("INSERT INTO is_game_url(topic_url, is_game) VALUES (?, ?)" +
                    "ON CONFLICT (topic_url) DO UPDATE" +
                    " SET is_game = excluded.is_game;",
                url, isGameTopic);
        return i > 0;
    }

    @Override
    public Optional<Boolean> isGameTopic(String url)
    {
        List<Boolean> isGame = this.getJdbcTemplate().query("SELECT * FROM is_game_url WHERE topic_url = ?",
                IS_GAME_MAPPER, url);

        return Optional.ofNullable(DataAccessUtils.singleResult(isGame));
    }


    @Override
    public Map<String, Boolean> getAll()
    {
        Map<String,Boolean> isGameUrlMap = this.getJdbcTemplate().query("SELECT * FROM is_game_url",
                IS_GAME_URL_MAP_MAPPER);

        return isGameUrlMap;
    }

}
