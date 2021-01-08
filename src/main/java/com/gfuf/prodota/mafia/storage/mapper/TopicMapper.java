package com.gfuf.prodota.mafia.storage.mapper;

import com.gfuf.prodota.data.topic.MafiaTopic;
import com.gfuf.prodota.data.topic.TopicStatus;
import org.springframework.jdbc.core.RowMapper;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TopicMapper implements RowMapper<MafiaTopic>
{

    @Override
    public MafiaTopic mapRow(ResultSet resultSet, int i) throws SQLException
    {
        String name = resultSet.getString("name");
        URI uri = URI.create(resultSet.getString("url"));
        TopicStatus status = TopicStatus.valueOf(resultSet.getString("status"));
        Optional<URI> pictureUrl = Optional.ofNullable(resultSet.getString("picture_url")).map(URI::create);
        return MafiaTopic.builder().setName(name)
                .setUri(uri)
                .setStatus(status)
                .setPictureUrl(pictureUrl).build();
    }
}
