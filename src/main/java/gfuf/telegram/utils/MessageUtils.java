package gfuf.telegram.utils;

import gfuf.telegram.customer.Customer;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public class MessageUtils
{
    public static SendMessage createMessageTemplate(Customer customer) {
        return createMessageTemplate(customer.getPrivateChatId());
    }


    // Создаем шаблон SendMessage с включенным Markdown
    public static SendMessage createMessageTemplate(Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .enableMarkdown(true);
    }

    public static String buildFullUserName(Update update)
    {
        String firstName =  defaultIfBlankNull(update.getMessage().getFrom().getFirstName());
        String secondName = defaultIfBlankNull(" " + update.getMessage().getFrom().getLastName());
        return  firstName + secondName;
    }


    public static String buildUserName(Update update)
    {
        String userName = update.getMessage().getFrom().getUserName();
        return userName == null ? "" : String.format("(@%s)", userName);
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
}
