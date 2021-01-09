package com.gfuf.telegram.bot.receive.handler.impl;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.bot.receive.handler.UpdateHandler;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.bot.receive.handler.impl.utils.AdminAction;
import com.gfuf.telegram.domain.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.gfuf.telegram.utils.MessageUtils.*;


public class StartAdminHandler implements UpdateHandler
{
    private static final Logger logger = LoggerFactory.getLogger(StartAdminHandler.class.getCanonicalName());

    public final CustomerService customerService;

    public StartAdminHandler(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        logger.debug(buildLogMessage(updateWrapper));

        String firstName = updateWrapper.getFirstName();
        customerService.saveCustomer(customer, State.ACTION_ADMIN);

        return handleTextMessage(customer, firstName);
    }

    @Override
    public State getState()
    {
        return State.START_ADMIN;
    }

    private String buildLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();

        return String.format("Admin Старт с %s %s [ id = %s ] )",  userFullName, userName, userId);
    }

    private List<BotApiMethod<? extends Serializable>> handleTextMessage(Customer customer, String firstName)
    {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                createOneButtonRow(AdminAction.ADD_TOPIC);
        List<InlineKeyboardButton> inlineKeyboardButtonsRowTwo =
                createOneButtonRow(AdminAction.DELETE_TOPIC);
        List<InlineKeyboardButton> inlineKeyboardButtonsRowThree =
                createOneButtonRow(AdminAction.ADD_ADMIN);
        List<InlineKeyboardButton> inlineKeyboardButtonsRowFour =
                createOneButtonRow(AdminAction.WRITE_REPORT);


        inlineKeyboardMarkup.setKeyboard(Arrays.asList(inlineKeyboardButtonsRowOne
                , inlineKeyboardButtonsRowTwo
                , inlineKeyboardButtonsRowThree
                , inlineKeyboardButtonsRowFour));


        return Arrays.asList(createMessageTemplate(customer)
                .setText(buildReceiveMessage(firstName))
                .setReplyMarkup(inlineKeyboardMarkup));
    }


    private String buildReceiveMessage(String firstName)
    {
        return String.format("%s, можешь выбрать одно из следующих действий",
                firstName);
    }
}
