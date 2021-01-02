package gfuf.prodota.mafia.storage.mapper;

import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.prodota.data.topic.TopicStatus;
import org.springframework.jdbc.core.RowMapper;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class IsGameMapper implements RowMapper<Boolean>
{
    @Override
    public Boolean mapRow(ResultSet resultSet, int i) throws SQLException
    {
        return resultSet.getBoolean("is_game");
    }
}
