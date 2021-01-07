package gfuf.telegram.bot.receive.handler.impl;

import gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.bot.receive.handler.impl.utils.OtherAction;
import gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.domain.UpdateWrapper;
import gfuf.telegram.utils.MessageUtils;
import gfuf.utils.UriHelper;
import org.apache.commons.validator.routines.UrlValidator;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;


public class AddTopicAdminHandler implements UpdateHandler
{
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
    public List<SendMessage> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        if (updateWrapper.hasMesageText())
        {
            System.out.println(buildLogMessage(updateWrapper));
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
                System.out.println(buildCancelLogMessage(updateWrapper));

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

    private List<SendMessage> ifSuccess(Customer customer, UpdateWrapper updateWrapper)
    {
        SendMessage successMessage = MessageUtils.createMessageTemplate(customer)
                .setText(SUCCESS_MESSAGE);
        List<SendMessage> messages = new ArrayList<>();
        messages.add(successMessage);

        customerService.saveCustomer(customer, State.START_ADMIN);
        List<SendMessage> startAdminMessages = updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
        messages.addAll(startAdminMessages);

        return messages;
    }

    private List<SendMessage> ifNotSuccess(Customer customer, UpdateWrapper updateWrapper)
    {
        SendMessage successMessage = MessageUtils.createMessageTemplate(customer)
                .setText(NOT_SUCCESS_MESSAGE);
        List<SendMessage> messages = new ArrayList<>();
        messages.add(successMessage);

        customerService.saveCustomer(customer, State.START_ADMIN);
        List<SendMessage> startAdminMessages = updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
        messages.addAll(startAdminMessages);

        return messages;
    }

    private List<SendMessage> ifIncorrectUrl(Customer customer, UpdateWrapper updateWrapper)
    {
        return ifIncorrect(customer, updateWrapper, INCORRECT_URL_MESSAGE);
    }

    private List<SendMessage> ifIncorrect(Customer customer, UpdateWrapper updateWrapper)
    {
        return ifIncorrect(customer, updateWrapper, INCORRECT_DEFAULT_MESSAGE);
    }

    private List<SendMessage> ifIncorrect(Customer customer, UpdateWrapper updateWrapper, String message)
    {
        System.out.println("incorrect add topic " + updateWrapper);

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
