package com.gfuf.telegram.customer.dao.storage.initer;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.customer.dao.storage.CustomerDAO;
import com.gfuf.telegram.customer.CustomerRole;

import javax.annotation.PostConstruct;

public class CustomerAdminIniter
{
    private final int telegram_user_id;

    private final long private_chat_id;

    private final CustomerDAO customerDAO;

    public CustomerAdminIniter(int telegram_user_id, long private_chat_id, CustomerDAO customerDAO)
    {
        this.telegram_user_id = telegram_user_id;
        this.private_chat_id = private_chat_id;
        this.customerDAO = customerDAO;
    }

    @PostConstruct
    public void init()
    {
        customerDAO.saveCustomerIfNotExists(telegram_user_id, CustomerRole.ADMIN, State.START_ADMIN, private_chat_id );
    }
}
