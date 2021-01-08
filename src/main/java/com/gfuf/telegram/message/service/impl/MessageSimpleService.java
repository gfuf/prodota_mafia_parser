package com.gfuf.telegram.message.service.impl;

import com.gfuf.prodota.data.topic.MafiaTopic;
import com.gfuf.telegram.message.service.MessageService;
import com.gfuf.telegram.message.storage.MessageMappingDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public class MessageSimpleService implements MessageService
{
    private static final Logger logger = LoggerFactory.getLogger(MessageSimpleService.class.getCanonicalName());

    private final MessageMappingDAO messageMappingDAO;

    private final long anouncerChatId;

    public MessageSimpleService(MessageMappingDAO messageMappingDAO, long anouncerChatId)
    {
        this.messageMappingDAO = messageMappingDAO;
        this.anouncerChatId = anouncerChatId;
    }

    @Override
    public Optional<Integer> messageIdByTopicUrl(String url)
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

    @Override
    public long getAnouncerChatId()
    {
        return anouncerChatId;
    }
}
