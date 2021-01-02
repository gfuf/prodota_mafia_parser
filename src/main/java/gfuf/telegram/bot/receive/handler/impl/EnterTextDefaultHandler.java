package gfuf.telegram.bot.receive.handler.impl;

import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static gfuf.telegram.utils.MessageUtils.*;

public class EnterTextDefaultHandler  implements UpdateHandler
{
    @Override
    public List<SendMessage> handle(Customer customer, Update update, CustomerService customerService)
    {
        System.out.println(buildLogMessage(update));

        return List.of();
    }

    private String buildLogMessage(Update update)
    {
        Integer userId = update.getMessage().getFrom().getId();
        String userFullName = buildFullUserName(update);
        String userName = buildUserName(update);
        String text = update.getMessage().getText();

        return String.format("от %s %s [ id = %s ] ) : %s", userFullName, userName, userId, text);
    }
}