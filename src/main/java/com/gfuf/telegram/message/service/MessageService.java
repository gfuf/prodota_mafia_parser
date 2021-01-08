package com.gfuf.telegram.message.service;

import com.gfuf.prodota.data.topic.MafiaTopic;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface MessageService
{
    Optional<Integer> messageIdByTopicUrl(String url);

    boolean tryWriteMapping(Message message, MafiaTopic topic);

    boolean writeMapping(int messageId, String url);

    //все сообщения которыми оперирует данный сервис, относятся к этому чату
    long getAnouncerChatId();
}
