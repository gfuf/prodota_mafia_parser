package com.gfuf.telegram.customer.dao.storage;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.CustomerRole;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO
{
    Optional<Customer> getCustomer(Integer telegramUserId);

    Customer saveCustomer(Integer telegramUserId, CustomerRole customerRole, State state,
                          Long privateChatId);

    Customer saveCustomerIfNotExists(Integer telegramUserId, CustomerRole customerRole, State state,
                                     Long privateChatId);

    List<Customer> getAll();
}
