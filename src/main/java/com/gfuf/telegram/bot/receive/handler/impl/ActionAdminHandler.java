package com.gfuf.telegram.bot.receive.handler.impl;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.bot.receive.handler.UpdateHandler;
import com.gfuf.telegram.bot.receive.handler.impl.utils.OtherAction;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.utils.MessageUtils;
import com.gfuf.telegram.bot.receive.handler.impl.utils.AdminAction;
import com.gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import com.gfuf.telegram.domain.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionAdminHandler implements UpdateHandler
{
    private static final Logger logger = LoggerFactory.getLogger(ActionAdminHandler.class.getCanonicalName());

    private static final String INCORRECT_DEFAULT_MESSAGE = "Выберите действие";

    private final CustomerService customerService;

    private final UpdateHandlersHolder updateHandlersHolder;

    public ActionAdminHandler(CustomerService customerService, UpdateHandlersHolder updateHandlersHolder)
    {
        this.customerService = customerService;
        this.updateHandlersHolder = updateHandlersHolder;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(Customer customer, UpdateWrapper updateWrapper)
    {

        if (updateWrapper.hasCallbackQuery())
        {
            logger.debug(buildLogMessage(updateWrapper));
            AdminAction adminAction = AdminAction.fromCommand(updateWrapper.getCallbacQueryData());
            if(adminAction != null)
            {
                customerService.saveCustomer(Customer.builder()
                        .from(customer)
                        .setState(adminAction.getNextState())
                        .build());
                return List.of(MessageUtils.createMessageTemplate(customer)
                        .setText(adminAction.getMessageText())
                        .setReplyMarkup(createCancelKey()));
            }
        }

        return ifIncorrect(customer, updateWrapper);
    }

    @Override
    public State getState()
    {
        return State.ACTION_ADMIN;
    }

    private InlineKeyboardMarkup createCancelKey()
    {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne =
                MessageUtils.createOneButtonRow(OtherAction.CANCEL);


        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        return inlineKeyboardMarkup;
    }

    private List<BotApiMethod<? extends Serializable>> ifIncorrect(Customer customer, UpdateWrapper updateWrapper)
    {
        logger.warn("неверный формат сообщения action admin {}", updateWrapper);

        SendMessage incorrectActionMessage = MessageUtils.createMessageTemplate(customer)
                .setText(INCORRECT_DEFAULT_MESSAGE);
        List<BotApiMethod<? extends Serializable>> messages = new ArrayList<>();
        messages.add(incorrectActionMessage);

        customerService.saveCustomer(customer, State.START_ADMIN);
        List<BotApiMethod<? extends Serializable>> startAdminMessages = updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
        messages.addAll(startAdminMessages);

        return messages;
    }

    private String buildLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();
        String callbacQueryData = updateWrapper.getCallbacQueryData();

        return String.format("выбор действия от %s %s [ id = %s ]  : действие = %s", userFullName, userName, userId, callbacQueryData);
    }
}
