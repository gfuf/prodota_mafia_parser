package com.gfuf.telegram.customer.dao.storage.impl;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.storage.CustomerDAO;
import com.gfuf.telegram.customer.CustomerRole;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomerCacheDAO implements CustomerDAO
{
    private final CustomerDAO customerDAO;

    private final Map<Integer, Customer> cache = new ConcurrentHashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CustomerCacheDAO(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    @PostConstruct
    public void init()
    {
        cache.putAll(customerDAO.getAll()
                    .stream()
                    .collect(Collectors.toMap(Customer::getTelegramUserId, Function.identity())));
    }

    @Override
    public Optional<Customer> getCustomer(Integer telegramUserId)
    {
        return Optional.ofNullable(cache.get(telegramUserId));
    }

    @Override
    public Customer saveCustomer(Integer telegramUserId, CustomerRole customerRole, State state, Long privateChatId)
    {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try
        {
            Customer customer = customerDAO.saveCustomer(telegramUserId, customerRole, state, privateChatId);
            cache.put(customer.getTelegramUserId(), customer);

            return customer;
        }
        finally
        {
            writeLock.unlock();
        }
    }

    @Override
    public Customer saveCustomerIfNotExists(Integer telegramUserId, CustomerRole customerRole, State state, Long privateChatId)
    {
        Customer customer = cache.get(telegramUserId);
        if(customer == null)
        {
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            try
            {
                customer = cache.get(telegramUserId);
                if (customer == null)
                {
                    customer = customerDAO.saveCustomer(telegramUserId, customerRole, state, privateChatId);
                    cache.put(customer.getTelegramUserId(), customer);
                }

            }
            finally
            {
                writeLock.unlock();
            }
        }

        return customer;
    }

    @Override
    public List<Customer> getAll()
    {
        return new ArrayList<>(cache.values());
    }
}
