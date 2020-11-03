package gfuf.prodota.mafia.storage.mapper;

import gfuf.prodota.data.Topic;
import org.springframework.jdbc.core.RowMapper;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicMapper implements RowMapper<Topic>
{

    @Override
    public Topic mapRow(ResultSet resultSet, int i) throws SQLException
    {
        String name = resultSet.getString("name");
        String url = resultSet.getString("url");
        return new Topic(name, URI.create(url));
    }
}
