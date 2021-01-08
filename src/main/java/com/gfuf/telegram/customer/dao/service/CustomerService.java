package com.gfuf.telegram.customer.dao.service;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.CustomerRole;

import java.util.Optional;

public interface CustomerService
{
    Optional<Customer> getCustomer(Integer telegramUserId);

    Customer saveCustomer(Integer telegramUserId, CustomerRole customerRole, State state, Long privateChatId);

    default Customer saveCustomer(Customer customer)
    {
        return saveCustomer(customer.getTelegramUserId(), customer.getRole(), customer.getState(), customer.getPrivateChatId());
    }

    default Customer saveCustomer(Customer customer, State newState)
    {
        return saveCustomer(Customer.builder()
                .from(customer)
                .setState(newState)
                .build());
    }

    Customer saveCustomerIfNotExists(Integer telegramUserId, CustomerRole customerRole, State state, Long privateChatId);
}
