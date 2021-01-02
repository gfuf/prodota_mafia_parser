package gfuf.telegram.bot.receive.handler.impl;

import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class StartAdminHandler implements UpdateHandler
{
    @Override
    public List<SendMessage> handle(Customer customer, Update update, CustomerService customerService)
    {
        return List.of();
    }
}
