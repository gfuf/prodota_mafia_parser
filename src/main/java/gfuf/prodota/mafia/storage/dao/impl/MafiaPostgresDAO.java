package gfuf.prodota.mafia.storage.dao.impl;

import gfuf.prodota.data.Topic;
import gfuf.prodota.mafia.storage.dao.MafiaDAO;
import gfuf.prodota.mafia.storage.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    public Optional<Topic> findLastGameTopic()
    {
        List<Topic> topic = this.getJdbcTemplate().query("SELECT * FROM topics WHERE id = (SELECT MAX(id) FROM topics)",
                TOPIC_MAPPER);

        return Optional.ofNullable(DataAccessUtils.singleResult(topic));
    }

    @Override
    public boolean writeLastGameTopic(Topic topic)
    {
        int i = this.getJdbcTemplate().update("INSERT INTO topics(name, url) VALUES (?, ?);",
                topic.getName(),
                topic.getUri().toString());
        return i > 0;
    }
}
