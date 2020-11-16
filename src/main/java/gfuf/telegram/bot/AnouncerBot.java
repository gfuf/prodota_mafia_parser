package gfuf.telegram.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AnouncerBot extends TelegramLongPollingBot
{
    @Value("${telegram.bot.name}")
    public String name;

    @Value("${telegram.bot.token}")
    private String token;


    @Override
    public void onUpdateReceived(Update update)
    {
        System.out.println("tut");
        //TODO
    }

    @Override
    public String getBotUsername()
    {
        return name;
    }

    @Override
    public String getBotToken()
    {
        return token;
    }

}
