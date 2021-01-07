package gfuf.telegram.bot.receive;

import gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import gfuf.telegram.converter.UpdateConverter;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.CustomerRole;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.domain.UpdateWrapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

public class UpdateReceiver
{
    private final CustomerService customerService;

    private final UpdateConverter updateConverter;

    private final UpdateHandlersHolder updateHandlersHolder;

    public UpdateReceiver(CustomerService customerService,
                          UpdateConverter updateConverter,
                          UpdateHandlersHolder updateHandlersHolder)
    {
        this.customerService = customerService;
        this.updateConverter = updateConverter;
        this.updateHandlersHolder = updateHandlersHolder;
    }

    public List<SendMessage> handle(Update update)
    {
        UpdateWrapper updateWrapper = updateConverter.convert(update);

        if(!updateWrapper.isPrivateMessage())
        {
            return List.of();
        }
        Integer telegramUserId = updateWrapper.getTelegramUserId();
        Long privateChatId = updateWrapper.getChatId();

        Customer customer = getCustomer(telegramUserId, privateChatId);

        State state = customer.getState();
        UpdateHandler handler =  updateHandlersHolder.get(state);
        return handler.handle(customer, updateWrapper);
    }

    private Customer getCustomer(Integer telegramUserId, Long privateChatId)
    {
        Optional<Customer> fromCache = customerService.getCustomer(telegramUserId);
        Customer customer = null;
        if (fromCache.isPresent())
        {
            customer = updatePrivateId(fromCache.get(), privateChatId);
        }
        else
        {
            customer = customerService.saveCustomerIfNotExists(telegramUserId, CustomerRole.DEFAULT,
                    State.START_DEFAULT, privateChatId);
        }

        return customer;
    }

    public Customer updatePrivateId(Customer customer, Long privateChatId)
    {
        if(customer.getPrivateChatId() == null)
        {
            customer = Customer.builder().from(customer)
                    .setPrivateChatId(privateChatId)
                    .build();
            customerService.saveCustomer(customer);
        }
        return customer;
    }

}
