package gfuf.telegram.customer.dao.mapper;

import gfuf.telegram.bot.receive.State;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.CustomerRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper  implements RowMapper<Customer>
{
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException
    {
        Integer id =  resultSet.getInt("id");
        Integer telegramUserId = resultSet.getInt("telegram_user_id");
        CustomerRole role = CustomerRole.valueOf(resultSet.getString("role"));
        State state = State.valueOf(resultSet.getString("state"));
        Long privateChatId = resultSet.getLong("private_chat_id");


        return Customer.builder()
                .setId(id)
                .setTelegramUserId(telegramUserId)
                .setRole(role)
                .setState(state)
                .setPrivateChatId(privateChatId).build();
    }
}
