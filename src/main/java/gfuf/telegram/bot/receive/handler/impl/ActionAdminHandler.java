package gfuf.telegram.bot.receive.handler.impl;

import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.bot.receive.handler.impl.utils.AdminAction;
import gfuf.telegram.bot.receive.handler.impl.utils.OtherAction;
import gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.domain.UpdateWrapper;
import gfuf.telegram.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static gfuf.telegram.utils.MessageUtils.*;

public class ActionAdminHandler implements UpdateHandler
{

    private static final String INCORRECT_DEFAULT_MESSAGE = "Выберите действие";

    private final CustomerService customerService;

    private final UpdateHandlersHolder updateHandlersHolder;

    public ActionAdminHandler(CustomerService customerService, UpdateHandlersHolder updateHandlersHolder)
    {
        this.customerService = customerService;
        this.updateHandlersHolder = updateHandlersHolder;
    }

    @Override
    public List<SendMessage> handle(Customer customer, UpdateWrapper updateWrapper)
    {

        if (updateWrapper.hasCallbackQuery())
        {
            System.out.println(buildLogMessage(updateWrapper));
            AdminAction adminAction = AdminAction.fromCommand(updateWrapper.getCallbacQueryData());
            if(adminAction != null)
            {
                customerService.saveCustomer(Customer.builder()
                        .from(customer)
                        .setState(adminAction.getNextState())
                        .build());
                return List.of(createMessageTemplate(customer)
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
                createOneButtonRow(OtherAction.CANCEL);


        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        return inlineKeyboardMarkup;
    }

    private List<SendMessage> ifIncorrect(Customer customer, UpdateWrapper updateWrapper)
    {
        System.out.println("incorrect action admin " + updateWrapper);

        SendMessage incorrectActionMessage = MessageUtils.createMessageTemplate(customer)
                .setText(INCORRECT_DEFAULT_MESSAGE);
        List<SendMessage> messages = new ArrayList<>();
        messages.add(incorrectActionMessage);

        customerService.saveCustomer(customer, State.START_ADMIN);
        List<SendMessage> startAdminMessages = updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
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
