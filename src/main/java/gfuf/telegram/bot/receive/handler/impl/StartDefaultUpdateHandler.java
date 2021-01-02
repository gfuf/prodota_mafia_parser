package gfuf.telegram.bot.receive.handler.impl;

import gfuf.telegram.bot.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import static gfuf.telegram.utils.MessageUtils.*;

import java.util.List;

public class StartDefaultUpdateHandler implements UpdateHandler
{
    @Override
    public List<SendMessage> handle(Customer customer, Update update, CustomerService customerService)
    {
        System.out.println(buildLogMessage(update));
        SendMessage welcomeMessage = MessageUtils.createMessageTemplate(customer)
                .setText(buildReceiveMessage(update));

        //меняем состояние
        customerService.saveCustomer(Customer.builder()
                .from(customer)
                .setState(State.ENTER_TEXT_DEFAULT)
                .build());

        return List.of(welcomeMessage);
    }

    private String buildLogMessage(Update update)
    {
        Integer userId = update.getMessage().getFrom().getId();
        String userFullName = buildFullUserName(update);
        String userName = buildUserName(update);

        return String.format("Старт с %s %s [ id = %s ] )",  userFullName, userName, userId);
    }

    private String buildReceiveMessage(Update update)
    {
        Integer userId = update.getMessage().getFrom().getId();
        String userFullName = buildFullUserName(update);
        String userName = buildUserName(update);
        String text = update.getMessage().getText();

        return String.format("Привет %s, можешь оставлять сообщения с фидбеками",
                userFullName);
    }
}
