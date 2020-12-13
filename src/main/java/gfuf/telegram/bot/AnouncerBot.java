package gfuf.telegram.bot;

import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.prodota.data.topic.Topic;
import gfuf.prodota.data.topic.TopicStatus;
import gfuf.telegram.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

//TODO завести конфигурацию, а не аннотациями
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

    @Autowired
    private MessageService messageService;


    @Override
    public void onUpdateReceived(Update update)
    {
    
    }

    public boolean sendToAnouncerChat(MafiaTopic topic)
    {
        boolean success;
        if (topic.getPictureUrl().isPresent())
        {
            success = sendPitctureToAnouncerChat(topic);
        } else
        {
            success = sendMessageToAnouncerChat(topic);
        }
        return success;
    }

    private boolean sendPitctureToAnouncerChat(MafiaTopic topic)
    {
        String text = buildText(topic);

        SendPhoto sendPhoto = new SendPhoto()
                .setChatId(anouncerChatId)
                .setPhoto(topic.getPictureUrl().get().toString())
                .setCaption(text)
                .setParseMode("HTML");
        boolean success;
        try
        {
            Message message = execute(sendPhoto);
            success = true;
            messageService.tryWriteMapping(message, topic);
        } catch (TelegramApiException e)
        {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, anouncerChatId, e.getMessage());
            success = false;
        }

        return success;
    }

    private boolean sendMessageToAnouncerChat(MafiaTopic topic)
    {
        String text = buildText(topic);

        SendMessage sendMessage = new SendMessage()
                .setChatId(anouncerChatId)
                .setText(text)
                .setParseMode("HTML");
        boolean success;
        try
        {
            Message message = execute(sendMessage);
            success = true;
            messageService.tryWriteMapping(message, topic);
        } catch (TelegramApiException e)
        {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", text, anouncerChatId, e.getMessage());
            success = false;
        }

        return success;
    }

    public boolean editMessage(MafiaTopic topic)
    {
        Optional<Integer> chatId = messageService
                .chatIdByUrl(topic.getUri().toString());

        if(chatId.isEmpty())
        {
            logger.error("Попытка редактировать сообщение, по урлу которого нет в DAO");
            return false;
        }

        boolean success = true;
        success&=editPicture(topic, chatId.get());
        success&=editText(topic, chatId.get());
        return success;
    }

    private boolean editPicture(MafiaTopic topic, int messageId)
    {
        boolean success = true;
        if (topic.getPictureUrl().isPresent())
        {
            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
            inputMediaPhoto.setMedia(topic.getPictureUrl().get().toString());


            EditMessageMedia msg = new EditMessageMedia()
                    .setChatId(anouncerChatId)
                    .setMessageId(messageId);
            msg.setMedia(inputMediaPhoto);
            try
            {
                execute(msg);
            } catch (TelegramApiException e)
            {
                success = false;
                logger.error("Failed to edit media message pictureUrl = {}, messageId = {} to {} due to error: {}",
                        topic.getPictureUrl().get(), messageId, anouncerChatId, e.getMessage());
            }
        }

        return success;
    }

    private boolean editText(MafiaTopic topic, int messageId)
    {
        boolean success = true;
        String caption = buildText(topic);
        EditMessageCaption msg = new EditMessageCaption()
                .setChatId(String.valueOf(anouncerChatId))
                .setMessageId(messageId)
                .setCaption(caption);
        try
        {
            execute(msg);
        } catch (TelegramApiException e)
        {
            success = false;
            logger.error("Failed to edit caption message caption = {},  messageId = {} to {} due to error: {}",
                    caption, messageId, anouncerChatId, e.getMessage());
        }
        return success;
    }

    private String buildText(MafiaTopic topic)
    {
        return buildUrlString(topic) + "\n"
                + buildStatusString(topic.getStatus());
    }


    private String buildUrlString(MafiaTopic topic)
    {
        return "<a href=\"" + topic.getUri() + "\">" + topic.getName() + "</a>";
    }

    private String buildStatusString(TopicStatus status)
    {
        String result = null;
        if (TopicStatus.OPEN.equals(status))
        {
            result = "Тема <b>открыта</b>";
        } else if (TopicStatus.CLOSED.equals(status))
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
