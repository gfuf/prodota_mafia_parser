package com.gfuf.telegram.bot.receive.handler.impl;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.bot.receive.handler.UpdateHandler;
import com.gfuf.telegram.bot.receive.handler.impl.utils.OtherAction;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.utils.MessageUtils;
import com.vdurmont.emoji.EmojiParser;
import com.gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import com.gfuf.telegram.domain.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WriteReportAdminHandler implements UpdateHandler
{
    private static final Logger logger = LoggerFactory.getLogger(WriteReportAdminHandler.class.getCanonicalName());

    private static final Logger loggerFeedback = LoggerFactory.getLogger("FeedbackLogger");

    private static final String OK = EmojiParser.parseToUnicode(":ok_hand:");

    public final CustomerService customerService;

    private final UpdateHandlersHolder updateHandlersHolder;

    public WriteReportAdminHandler(CustomerService customerService,
                                   UpdateHandlersHolder updateHandlersHolder)
    {
        this.customerService = customerService;
        this.updateHandlersHolder = updateHandlersHolder;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        if (updateWrapper.hasMesageText())
        {
            loggerFeedback.debug(buildLogMessage(updateWrapper));

            return answer(customer, updateWrapper);
        }
        else if(updateWrapper.hasCallbackQuery())
        {
            String command = updateWrapper.getCallbacQueryData();
            if(OtherAction.CANCEL.isThisCommand(command))
            {
                logger.debug(buildCancelLogMessage(updateWrapper));

                customerService.saveCustomer(customer, State.START_ADMIN);
                return updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
            }
        }

        return ifIncorrect(customer, updateWrapper);
    }

    @Override
    public State getState()
    {
        return State.WRITE_REPORT_ADMIN;
    }

    private List<BotApiMethod<? extends Serializable>> answer(Customer customer, UpdateWrapper updateWrapper)
    {
        SendMessage message = MessageUtils.createMessageTemplate(customer)
                .setText(OK);
        List<BotApiMethod<? extends Serializable>> messages = new ArrayList<>();
        messages.add(message);

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
        String text = updateWrapper.getMessageText();

        return String.format("от %s %s [ id = %s ] ) : %s", userFullName, userName, userId, text);
    }

    private String buildCancelLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();

        return String.format("отмена отправки фидбека от %s %s [ id = %s ]", userFullName, userName, userId);
    }

    private List<BotApiMethod<? extends Serializable>> ifIncorrect(Customer customer, UpdateWrapper updateWrapper)
    {
        logger.warn("incorrect write report " + updateWrapper);

        customerService.saveCustomer(customer, State.START_ADMIN);
        return updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
    }
}
