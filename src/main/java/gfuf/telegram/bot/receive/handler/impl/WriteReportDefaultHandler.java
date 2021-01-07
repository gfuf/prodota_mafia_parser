package gfuf.telegram.bot.receive.handler.impl;

import com.vdurmont.emoji.EmojiParser;
import gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.domain.UpdateWrapper;
import gfuf.telegram.utils.MessageUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


import java.util.List;

public class WriteReportDefaultHandler implements UpdateHandler
{

    private static final String OK = EmojiParser.parseToUnicode(":ok_hand:");

    public final CustomerService customerService;

    public WriteReportDefaultHandler(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @Override
    public List<SendMessage> handle(Customer customer, UpdateWrapper updateWrapper)
    {
        System.out.println(buildLogMessage(updateWrapper));

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