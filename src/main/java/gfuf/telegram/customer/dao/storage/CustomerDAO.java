package gfuf.telegram.customer.dao.storage;

import gfuf.telegram.bot.State;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.CustomerRole;

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
