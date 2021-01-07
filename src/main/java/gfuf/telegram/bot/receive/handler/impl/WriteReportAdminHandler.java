package gfuf.telegram.bot.receive.handler.impl;

import com.vdurmont.emoji.EmojiParser;
import gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.bot.receive.handler.impl.utils.OtherAction;
import gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.domain.UpdateWrapper;
import gfuf.telegram.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class WriteReportAdminHandler implements UpdateHandler
{
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
    public List<SendMessage> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        if (updateWrapper.hasMesageText())
        {
            System.out.println(buildLogMessage(updateWrapper));

            return answer(customer, updateWrapper);
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
        return State.WRITE_REPORT_ADMIN;
    }

    private List<SendMessage> answer(Customer customer, UpdateWrapper updateWrapper)
    {
        SendMessage message = MessageUtils.createMessageTemplate(customer)
                .setText(OK);
        List<SendMessage> messages = new ArrayList<>();
        messages.add(message);

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

    private List<SendMessage> ifIncorrect(Customer customer, UpdateWrapper updateWrapper)
    {
        System.out.println("incorrect write report " + updateWrapper);

        customerService.saveCustomer(customer, State.START_ADMIN);
        return updateHandlersHolder.get(State.START_ADMIN).handle(customer, updateWrapper);
    }
}
