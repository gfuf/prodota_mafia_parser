package gfuf.telegram.bot.receive.handler.impl;

import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.domain.UpdateWrapper;
import gfuf.telegram.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class StartDefaultUpdateHandler implements UpdateHandler
{
    public final CustomerService customerService;

    public StartDefaultUpdateHandler(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @Override
    public List<SendMessage> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        System.out.println(buildLogMessage(updateWrapper));

        customerService.saveCustomer(customer, State.WRITE_REPORT_DEFAULT);
        SendMessage welcomeMessage = MessageUtils.createMessageTemplate(customer)
                .setText(buildReceiveMessage(updateWrapper));
        return List.of(welcomeMessage);
    }

    @Override
    public State getState()
    {
        return State.START_DEFAULT;
    }

    private String buildLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();

        return String.format("Default Старт с %s %s [ id = %s ] )",  userFullName, userName, userId);
    }

    private String buildReceiveMessage(UpdateWrapper updateWrapper)
    {
        String firstName = updateWrapper.getFirstName();


        return String.format("Привет %s, можешь оставлять сообщения с фидбеками",
                firstName);
    }
}
