package gfuf.telegram.customer.dao.storage.impl;

import gfuf.telegram.bot.receive.State;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.CustomerRole;
import gfuf.telegram.customer.dao.mapper.CustomerMapper;
import gfuf.telegram.customer.dao.storage.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CastomerPostgresDAO extends JdbcDaoSupport implements CustomerDAO
{
    private static final CustomerMapper CUSTOMER_MAPPER = new CustomerMapper();

    @Autowired
    public CastomerPostgresDAO(DataSource dataSource)
    {
        this.setDataSource(dataSource);
    }

    @Override
    public Optional<Customer> getCustomer(Integer telegramUserId)
    {
        List<Customer> customers = this.getJdbcTemplate().query("SELECT * FROM customers WHERE telegram_user_id = ?",
                CUSTOMER_MAPPER, telegramUserId);

        return Optional.ofNullable(DataAccessUtils.singleResult(customers));
    }

    @Override
    public Customer saveCustomer(Integer telegramUserId, CustomerRole customerRole, State state, Long privateChatId)
    {
        this.getJdbcTemplate().update("INSERT INTO customers(telegram_user_id, role, state, private_chat_id) VALUES (?, ?, ? ,?)" +
                        "ON CONFLICT (telegram_user_id) DO UPDATE"
                        + " SET role = excluded.role,"
                        + " state = excluded.state,"
                        + " private_chat_id = excluded.private_chat_id",
                telegramUserId, customerRole.name(), state.name(), privateChatId);

        return getCustomer(telegramUserId).get();
    }

    @Override
    public Customer saveCustomerIfNotExists(Integer telegramUserId, CustomerRole customerRole, State state, Long privateChatId)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Customer> getAll()
    {
        return this.getJdbcTemplate().query("SELECT * FROM customers", CUSTOMER_MAPPER);
    }
}
