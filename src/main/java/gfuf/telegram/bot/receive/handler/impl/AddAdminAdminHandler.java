package gfuf.telegram.bot.receive.handler.impl;

import com.google.common.primitives.Ints;
import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.bot.receive.handler.impl.utils.OtherAction;
import gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.CustomerRole;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.domain.UpdateWrapper;
import gfuf.telegram.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AddAdminAdminHandler implements UpdateHandler
{
    private static final String INCORRECT_DEFAULT_MESSAGE = "Введите id или отмените действие";

    private static final String INCORRECT_TELEGRAM_ID_MESSAGE = "Введённый id некорректный, введите корректный id или отмените действие";

    private static final String SUCCESS_MESSAGE = "Успешно";

    private static final String EMPTY_CUSTOMER_BY_ID = "Не удалось найти чат с данным пользователем";

    private final CustomerService customerService;

    private final UpdateHandlersHolder updateHandlersHolder;

    public AddAdminAdminHandler(CustomerService customerService,
                                UpdateHandlersHolder updateHandlersHolder)
    {
        this.customerService = customerService;
        this.updateHandlersHolder = updateHandlersHolder;
    }

    @Override
    public List<SendMessage> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        if (updateWrapper.hasMesageText())
        {
            System.out.println(buildLogMessage(updateWrapper));

            String telegramIdText = updateWrapper.getMessageText();
            Integer telegramId = Ints.tryParse(telegramIdText);
            if (telegramId == null)
            {
                return ifIncorrectTelegramId(customer, updateWrapper);
            }
            Optional<Customer> customerById = customerService.getCustomer(telegramId);
            if (customerById.isEmpty())
            {
                return ifEmptyCustomerById(customer, updateWrapper);
            }

            customerService.saveCustomer(Customer.builder().from(customerById.get())
                    .setRole(CustomerRole.ADMIN)
                    .setState(State.START_ADMIN)
                    .build());
            return ifSuccess(customer, updateWrapper);
        }
        else if (updateWrapper.hasCallbackQuery())
        {
            String command = updateWrapper.getCallbacQueryData();
            if (OtherAction.CANCEL.isThisCommand(command))
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
        return State.ADD_ADMIN_ADMIN;
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

    private List<SendMessage> ifEmptyCustomerById(Customer customer, UpdateWrapper updateWrapper)
    {
        System.out.println(buildEmptyCustomerByIdLogMessage(updateWrapper));

        SendMessage message = MessageUtils.createMessageTemplate(customer)
                .setText(EMPTY_CUSTOMER_BY_ID);
        List<SendMessage> messages = new ArrayList<>();
        messages.add(message);

        customerService.saveCustomer(customer, State.START_ADMIN);
        List<SendMessage> startAdminMessages = updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
        messages.addAll(startAdminMessages);

        return messages;
    }

    private String buildEmptyCustomerByIdLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();
        String text = updateWrapper.getMessageText();

        return String.format("не получилось добавление админа от %s %s [ id = %s ] , т.к. не удалось найти чата  для id = %s", userFullName, userName, userId, text);
    }

    private List<SendMessage> ifIncorrectTelegramId(Customer customer, UpdateWrapper updateWrapper)
    {
        return ifIncorrect(customer, updateWrapper, INCORRECT_TELEGRAM_ID_MESSAGE);
    }

    private List<SendMessage> ifIncorrect(Customer customer, UpdateWrapper updateWrapper)
    {
        return ifIncorrect(customer, updateWrapper, INCORRECT_DEFAULT_MESSAGE);
    }

    private List<SendMessage> ifIncorrect(Customer customer, UpdateWrapper updateWrapper, String message)
    {
        System.out.println("incorrect add admin " + updateWrapper);

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

        return String.format("добавление админа от %s %s [ id = %s ]  : id = %s", userFullName, userName, userId, text);
    }

    private String buildCancelLogMessage(UpdateWrapper updateWrapper)
    {
        Integer userId = updateWrapper.getUserId();
        String userFullName = updateWrapper.getUserFullName();
        String userName = updateWrapper.getFormatUserName();

        return String.format("отмена добавления админа от %s %s [ id = %s ]", userFullName, userName, userId);
    }

}
