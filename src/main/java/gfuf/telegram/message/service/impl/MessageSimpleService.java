package gfuf.telegram.message.service.impl;

import gfuf.prodota.data.topic.MafiaTopic;
import gfuf.telegram.message.service.MessageService;
import gfuf.telegram.message.storage.MessageMappingDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public class MessageSimpleService implements MessageService
{
    private static final Logger logger = LoggerFactory.getLogger(MessageSimpleService.class);

    private final MessageMappingDAO messageMappingDAO;

    public MessageSimpleService(MessageMappingDAO messageMappingDAO)
    {
        this.messageMappingDAO = messageMappingDAO;
    }

    @Override
    public Optional<Integer> chatIdByUrl(String url)
    {
        return messageMappingDAO.chatIdByUrl(url);
    }

    @Override
    public boolean tryWriteMapping(Message message, MafiaTopic topic)
    {
        try
        {
            return writeMapping(message.getMessageId(), topic.getUri().toString());
        }
        catch (Exception e)
        {
            logger.error("Ошибка во время записи маппинга сообщения, message = {}, topic = {}",
                    message, topic, e);
            return false;
        }

    }

    @Override
    public boolean writeMapping(int messageId, String url)
    {
        return messageMappingDAO.writeMapping(messageId, url);
    }
}
