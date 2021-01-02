package gfuf.telegram.message.storage.impl;

import gfuf.telegram.message.storage.MessageMappingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageMappingPostgresDAO extends JdbcDaoSupport implements MessageMappingDAO
{
    @Autowired
    public MessageMappingPostgresDAO(DataSource dataSource)
    {
        this.setDataSource(dataSource);
    }


    @Override
    public Optional<Integer> chatIdByUrl(String url)
    {
        List<Integer> chatId = this.getJdbcTemplate().query("SELECT message_id FROM message_mapping WHERE topic_url = ?",
                (rw, i) -> rw.getInt("message_id"), url);

        return Optional.ofNullable(DataAccessUtils.singleResult(chatId));
    }

    @Override
    public boolean writeMapping(int messageId, String url)
    {
        int i = this.getJdbcTemplate().update("INSERT INTO message_mapping(message_id, topic_url) VALUES (?, ?);",
                messageId, url);
        return i > 0;
    }
}
