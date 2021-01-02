package gfuf.telegram.bot.receive;

import gfuf.telegram.bot.State;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.CustomerRole;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

public class UpdateReceiver
{
    private final CustomerService customerService;

    public UpdateReceiver(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    //TODO реагировать только на приватные чаты
    public List<SendMessage> handle(Update update)
    {
        Integer telegramUserId = update.getMessage().getFrom().getId();
        Long privateChatId = update.getMessage().getChatId();
        Customer customer = getCustomer(telegramUserId, privateChatId);
        State role = customer.getState();
        UpdateHandler handler = role.getHandler();

        return handler.handle(customer, update, customerService);
    }

    private Customer getCustomer(Integer telegramUserId, Long privateChatId)
    {
        Optional<Customer> customer = customerService.getCustomer(telegramUserId);
        return customer.isEmpty()
                ? customerService.saveCustomerIfNotExists(telegramUserId, CustomerRole.DEFAULT, State.START_DEFAULT, privateChatId)
                : customer.get();
    }

}
