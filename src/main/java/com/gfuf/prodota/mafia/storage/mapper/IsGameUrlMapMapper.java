package com.gfuf.prodota.mafia.storage.mapper;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class IsGameUrlMapMapper implements ResultSetExtractor<Map<String, Boolean>>
{
    @Override
    public Map<String, Boolean> extractData(ResultSet resultSet) throws SQLException, DataAccessException
    {
        Map<String, Boolean> map = new HashMap<>();
        while(resultSet.next()){
            map.put(resultSet.getString("topic_url"), resultSet.getBoolean("is_game"));
        }
        return map;
    }
}

