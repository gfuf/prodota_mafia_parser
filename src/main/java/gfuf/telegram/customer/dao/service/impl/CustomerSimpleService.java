package gfuf.telegram.customer.dao.service.impl;

import gfuf.telegram.bot.State;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.CustomerRole;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.customer.dao.storage.CustomerDAO;

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
