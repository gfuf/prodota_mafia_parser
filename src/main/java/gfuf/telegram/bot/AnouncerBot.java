package gfuf.telegram.bot;

import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.prodota.data.topic.TopicStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
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

    public boolean sendToAnouncerChat(MafiaTopic topic)
    {
        String message = buildUrlString(topic) + "\n"
                + buildStatusString(topic.getStatus());

        SendPhoto msg = new SendPhoto()
            .setChatId(anouncerChatId)
            .setPhoto(topic.getPictureUrl().get())//TODO а если нет
            .setCaption(message)
            .setParseMode("HTML");
        boolean success;
        try {
            execute(msg);
            success = true;
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", message, anouncerChatId, e.getMessage());
            success = false;
        }

        return success;
    }

    private String buildUrlString(MafiaTopic topic)
    {
        return "<a href=\""+ topic.getUri() +"\">"+ topic.getName()+"</a>";
    }
    private String buildStatusString(TopicStatus status)
    {
        String result = null;
        if(TopicStatus.OPEN.equals(status))
        {
            result = "Тема <b>открыта</b>";
        }
        else if(TopicStatus.CLOSED.equals(status))
        {
            result = "Тема <b>закрыта</b>";
        }
        return result;
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
