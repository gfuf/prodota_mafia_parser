package gfuf.telegram.bot.receive.handler;

import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import org.glassfish.jersey.internal.inject.Custom;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface UpdateHandler
{
    List<SendMessage> handle(Customer customer, Update update, CustomerService customerService);
}
