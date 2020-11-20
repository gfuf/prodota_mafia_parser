package gfuf.telegram.bot;

import gfuf.prodota.data.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AnouncerBot extends TelegramLongPollingBot
{
    private static final Logger logger = LoggerFactory.getLogger(AnouncerBot.class);

    @Value("${telegram.chat.id}")
    public long anouncerChatId;

    @Value("${telegram.bot.name}")
    public String name;

    @Value("${telegram.bot.token}")
    private String token;


    @Override
    public void onUpdateReceived(Update update)
    {

    }

    public boolean writeToAnouncerChat(Topic topic)
    {
        String message = topic.getName() + "\n" + topic.getUri();
        SendMessage response = new SendMessage();
        response.setChatId(anouncerChatId);
        response.setText(message);

        boolean success;
        try {
            execute(response);
            success = true;
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", message, anouncerChatId, e.getMessage());
            success = false;
        }

        return success;
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
