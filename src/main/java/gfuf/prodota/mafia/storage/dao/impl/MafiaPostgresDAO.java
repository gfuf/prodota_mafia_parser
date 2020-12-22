package gfuf.prodota.mafia.storage.dao.impl;

import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.prodota.mafia.storage.dao.MafiaDAO;
import gfuf.prodota.mafia.storage.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.net.URI;
import java.util.*;

@Repository
public class MafiaPostgresDAO extends JdbcDaoSupport implements MafiaDAO
{
    private static final TopicMapper TOPIC_MAPPER = new TopicMapper();

    @Autowired
    public MafiaPostgresDAO(DataSource dataSource)
    {
        this.setDataSource(dataSource);
    }

    @Override
    public Optional<MafiaTopic> lastTopic()
    {
        List<MafiaTopic> topic = this.getJdbcTemplate().query("SELECT * FROM topics WHERE id = (SELECT MAX(id) FROM topics)",
                TOPIC_MAPPER);

        return Optional.ofNullable(DataAccessUtils.singleResult(topic));
    }

    @Override
    public Optional<MafiaTopic> topicByUrl(String url)
    {
        List<MafiaTopic> topic = this.getJdbcTemplate().query("SELECT * FROM topics WHERE url = ?",
                TOPIC_MAPPER, url);

        return Optional.ofNullable(DataAccessUtils.singleResult(topic));
    }

    @Override
    public boolean writeTopic(MafiaTopic topic)
    {
        int i = this.getJdbcTemplate().update("INSERT INTO topics(name, url, status, picture_url) VALUES (?, ?, ?, ?)" +
                        "ON CONFLICT (url) DO UPDATE" +
                        " SET name = excluded.name," +
                        " status = excluded.status," +
                        " picture_url = excluded.picture_url;",
                topic.getName(),
                topic.getUri().toString(),
                topic.getStatus().name(),
                topic.getPictureUrl().map(URI::toString).orElse(null));
        return i > 0;
    }
}
