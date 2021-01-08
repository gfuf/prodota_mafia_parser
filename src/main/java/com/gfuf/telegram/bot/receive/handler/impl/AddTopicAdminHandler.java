package com.gfuf.telegram.bot.receive.handler.impl;

import com.gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.bot.receive.handler.UpdateHandler;
import com.gfuf.telegram.bot.receive.handler.impl.utils.OtherAction;
import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.utils.MessageUtils;
import com.gfuf.utils.UriHelper;
import com.gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import com.gfuf.telegram.domain.UpdateWrapper;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AddTopicAdminHandler implements UpdateHandler
{
    private static final Logger logger = LoggerFactory.getLogger(AddTopicAdminHandler.class.getCanonicalName());

    private static final String INCORRECT_DEFAULT_MESSAGE = "Введите url или отмените действие";

    private static final String INCORRECT_URL_MESSAGE = "Введённый url некорректный, введите корректный url или отмените действие";

    private static final String SUCCESS_MESSAGE = "Успешно";

    private static final String NOT_SUCCESS_MESSAGE = "Не удалось добавить топик";

    private static final UrlValidator urlValidator = new UrlValidator();

    public final CustomerService customerService;

    private final UpdateHandlersHolder updateHandlersHolder;

    private final MafiaIsGameCustomizableService mafiaIsGameCustomizableService;

    public AddTopicAdminHandler(CustomerService customerService,
                                UpdateHandlersHolder updateHandlersHolder,
                                MafiaIsGameCustomizableService mafiaIsGameCustomizableService)
    {
        this.customerService = customerService;
        this.updateHandlersHolder = updateHandlersHolder;
        this.mafiaIsGameCustomizableService = mafiaIsGameCustomizableService;
    }

    @Override
    public List<BotApiMethod<? extends Serializable>> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        if (updateWrapper.hasMesageText())
        {
            logger.debug(buildLogMessage(updateWrapper));
            String urlTopicText = updateWrapper.getMessageText();
            if (!urlValidator.isValid(urlTopicText))
            {
                return ifIncorrectUrl(customer, updateWrapper);
            }
            urlTopicText = UriHelper.createWithoutParameters(urlTopicText).toString();
            boolean success = mafiaIsGameCustomizableService.setIsGameTopic(urlTopicText, true);
            return success
                    ? ifSuccess(customer, updateWrapper)
                    : ifNotSuccess(customer, updateWrapper);
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
        return State.ADD_TOPIC_ADMIN;
    }

    private List<BotApiMethod<? extends Serializable>> ifSuccess(Customer customer, UpdateWrapper updateWrapper)
    {
        SendMessage successMessage = MessageUtils.createMessageTemplate(customer)
                .setText(SUCCESS_MESSAGE);
        List<BotApiMethod<? extends Serializable>> messages = new ArrayList<>();
        messages.add(successMessage);

        customerService.saveCustomer(customer, State.START_ADMIN);
        List<BotApiMethod<? extends Serializable>> startAdminMessages = updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
        messages.addAll(startAdminMessages);

        return messages;
    }

    private List<BotApiMethod<? extends Serializable>> ifNotSuccess(Customer customer, UpdateWrapper updateWrapper)
    {
        SendMessage successMessage = MessageUtils.createMessageTemplate(customer)
                .setText(NOT_SUCCESS_MESSAGE);
        List<BotApiMethod<? extends Serializable>> messages = new ArrayList<>();
        messages.add(successMessage);

        customerService.saveCustomer(customer, State.START_ADMIN);
        List<BotApiMethod<? extends Serializable>> startAdminMessages = updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
        messages.addAll(startAdminMessages);

        return messages;
    }

    private List<BotApiMethod<? extends Serializable>> ifIncorrectUrl(Customer customer, UpdateWrapper updateWrapper)
    {
        return ifIncorrect(customer, updateWrapper, INCORRECT_URL_MESSAGE);
    }

    private List<BotApiMethod<? extends Serializable>> ifIncorrect(Customer customer, UpdateWrapper updateWrapper)
    {
        return ifIncorrect(customer, updateWrapper, INCORRECT_DEFAULT_MESSAGE);
    }

    private List<BotApiMethod<? extends Serializable>> ifIncorrect(Customer customer, UpdateWrapper updateWrapper, String message)
    {
        logger.warn("неверный формат сообщения add topic {}", updateWrapper);

        SendMessage incorrectActionMessage = MessageUtils.createMessageTemplate(customer)
                .setText(message);
        return List.of(incorrectActionMessage);
    }

    private String buildLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();
        String text = updateWrapper.getMessageText();

        return String.format("добавление топика от %s %s [ id = %s ]  : url = %s", userFullName, userName, userId, text);
    }

    private String buildCancelLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();

        return String.format("отмена добавление топика от %s %s [ id = %s ]", userFullName, userName, userId);
    }
}
