package com.gfuf.telegram.utils;

import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.bot.receive.handler.impl.utils.KeyAction;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Objects;

public class MessageUtils
{
    public static SendMessage createMessageTemplate(Customer customer) {
        return createMessageTemplate(customer.getPrivateChatId());
    }

    public static SendMessage createMessageTemplate(Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .enableMarkdown(true);
    }


    public static String defaultIfBlankNull(String str)
    {
        if(str != null)
        {
            str = str.trim();
            if(!StringUtils.isBlank(str) && !Objects.equals("null", str))
            {
                return str;
            }
        }
        return "";
    }

    public static List<InlineKeyboardButton> createOneButtonRow(KeyAction action)
    {
        return List.of(createInlineKeyboardButton(action.getText(), action.getCommand()));
    }

    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command)
    {
        return new InlineKeyboardButton()
                .setText(text)
                .setCallbackData(command);
    }

}
