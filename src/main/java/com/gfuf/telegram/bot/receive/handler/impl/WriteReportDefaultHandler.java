package com.gfuf.telegram.bot.receive.handler.impl;

import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.bot.receive.handler.UpdateHandler;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.utils.MessageUtils;
import com.vdurmont.emoji.EmojiParser;
import com.gfuf.telegram.domain.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


import java.io.Serializable;
import java.util.List;

public class WriteReportDefaultHandler implements UpdateHandler
{
    private static final Logger logger = LoggerFactory.getLogger(WriteReportDefaultHandler.class.getCanonicalName());

    private static final Logger loggerFeedback = LoggerFactory.getLogger("FeedbackLogger");

    private static final String OK = EmojiParser.parseToUnicode(":ok_hand:");

    public final CustomerService customerService;

    public WriteReportDefaultHandler(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        loggerFeedback.debug(buildLogMessage(updateWrapper));

        SendMessage message = MessageUtils.createMessageTemplate(customer)
                .setText(OK);
        return List.of(message);
    }

    @Override
    public State getState()
    {
        return State.WRITE_REPORT_DEFAULT;
    }

    private String buildLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();
        String text = updateWrapper.getMessageText();

        return String.format("от %s %s [ id = %s ] ) : %s", userFullName, userName, userId, text);
    }
}