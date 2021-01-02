package gfuf.telegram.message.service;

import gfuf.prodota.data.topic.MafiaTopic;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

public interface MessageService
{
    Optional<Integer> chatIdByUrl(String url);

    boolean tryWriteMapping(Message message, MafiaTopic topic);

    boolean writeMapping(int messageId, String url);
}
