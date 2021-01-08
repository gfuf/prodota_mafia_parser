package com.gfuf.prodota.mafia.storage.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IsGameMapper implements RowMapper<Boolean>
{
    @Override
    public Boolean mapRow(ResultSet resultSet, int i) throws SQLException
    {
        return resultSet.getBoolean("is_game");
    }
}
