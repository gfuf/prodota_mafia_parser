package com.gfuf.telegram.bot.receive.handler.impl;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.bot.receive.handler.UpdateHandler;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.domain.UpdateWrapper;
import com.gfuf.telegram.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class StartDefaultUpdateHandler implements UpdateHandler
{
    private static final Logger logger = LoggerFactory.getLogger(StartDefaultUpdateHandler.class.getCanonicalName());

    public final CustomerService customerService;

    public StartDefaultUpdateHandler(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        logger.debug(buildLogMessage(updateWrapper));

        customerService.saveCustomer(customer, State.WRITE_REPORT_DEFAULT);
        SendMessage welcomeMessage = MessageUtils.createMessageTemplate(customer)
                .setText(buildReceiveMessage(updateWrapper));
        return Arrays.asList(welcomeMessage);
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
