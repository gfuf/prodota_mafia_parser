package gfuf.telegram.domain;

import antlr.debug.MessageAdapter;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static gfuf.telegram.utils.MessageUtils.*;

public class UpdateWrapper
{
    private final Update update;

    private final Chat chat;

    private final User user;

    public UpdateWrapper(Update update, Chat chat, User user)
    {
        this.update = update;
        this.user = user;
        this.chat = chat;

    }

    public Update getUpdate()
    {
        return update;
    }

    public String getMessageText()
    {
        return update.getMessage().getText();
    }

    public boolean hasMesageText()
    {
        return update.getMessage() != null && update.getMessage().getText()!=null;
    }


    public User getUser()
    {
        return user;
    }

    public boolean isPrivateMessage()
    {
       return chat.isUserChat();
    }

    public Integer getTelegramUserId()
    {
        return user.getId();
    }

    public Long getChatId()
    {
        return chat.getId();
    }

    public boolean hasCallbackQuery()
    {
        return update.hasCallbackQuery();
    }


    public String getCallbacQueryData()
    {
        return update.getCallbackQuery().getData();
    }

    public String getFirstName()
    {
        return user.getFirstName();
    }

    public Integer getUserId()
    {
        return user.getId();
    }

    public String getUserFullName()
    {
        String firstName = defaultIfBlankNull(user.getFirstName());
        String secondName = defaultIfBlankNull(" " + user.getLastName());
        return firstName + secondName;
    }

    public String getFormatUserName()
    {
        String userName = user.getUserName();
        return userName == null ? "" : String.format("(@%s)", userName);
    }


    @Override
    public String toString()
    {
        return "UpdateWrapper{" +
                "update=" + update +
                ", chat=" + chat +
                ", user=" + user +
                '}';
    }
}
