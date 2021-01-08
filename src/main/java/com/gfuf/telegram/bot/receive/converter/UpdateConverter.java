package com.gfuf.telegram.bot.receive.converter;

import com.gfuf.telegram.domain.UpdateWrapper;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class UpdateConverter
{
    public UpdateWrapper convert(Update update)
    {
        Chat chat =  getChat(update);
        User user = getUser(update);

        return new UpdateWrapper(update, chat, user);

    }

    private static Chat getChat(Update update)
    {
        Chat chat = null;
        if (update.getMessage() != null)
        {
            chat = update.getMessage().getChat();
        } else if (update.getCallbackQuery() != null)
        {
            chat = update.getCallbackQuery().getMessage().getChat();
        }

        if (chat == null)
        {
            throw new RuntimeException(String.format("Неподдерживаемый формат сообщения, %s ", update));
        }

        return chat;
    }

    private static User getUser(Update update)
    {
        User user = null;
        if (update.getMessage() != null)
        {
            user = update.getMessage().getFrom();
        } else if (update.getCallbackQuery() != null)
        {
            user = update.getCallbackQuery().getFrom();
        }

        if (user == null)
        {
            throw new RuntimeException(String.format("Неподдерживаемый формат сообщения, %s ", update));
        }

        return user;
    }
}
