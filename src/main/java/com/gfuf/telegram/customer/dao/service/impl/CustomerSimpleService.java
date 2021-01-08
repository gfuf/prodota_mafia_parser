package com.gfuf.telegram.customer.dao.service.impl;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.customer.dao.storage.CustomerDAO;
import com.gfuf.telegram.customer.CustomerRole;

import java.util.Optional;

public class CustomerSimpleService implements CustomerService
{
    private final CustomerDAO customerDAO;

    public CustomerSimpleService(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    @Override
    public Optional<Customer> getCustomer(Integer telegramUserId)
    {
        return customerDAO.getCustomer(telegramUserId);
    }

    @Override
    public Customer saveCustomer(Integer telegramUserId, CustomerRole customerRole, State state, Long privateChatId)
    {
        return customerDAO.saveCustomer(telegramUserId, customerRole, state, privateChatId);
    }

    @Override
    public Customer saveCustomerIfNotExists(Integer telegramUserId, CustomerRole customerRole,  State state, Long privateChatId)
    {
        return customerDAO.saveCustomerIfNotExists(telegramUserId, customerRole, state, privateChatId);
    }
}
