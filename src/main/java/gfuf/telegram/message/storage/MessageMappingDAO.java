package gfuf.telegram.message.storage;

import java.util.Optional;

public interface MessageMappingDAO
{
    Optional<Integer> chatIdByUrl(String url);

    boolean writeMapping(int chatId, String url);
}
