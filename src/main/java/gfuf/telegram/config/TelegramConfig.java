package gfuf.telegram.config;

import gfuf.prodota.parser.section.SectionParser;
import gfuf.telegram.service.MessageService;
import gfuf.telegram.service.impl.MessageSimpleService;
import gfuf.telegram.storage.MessageMappingDAO;
import gfuf.telegram.storage.impl.MessageMappingPostgresDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfig
{
    @Bean
    public MessageService messageService(MessageMappingDAO messageMappingDAO)
    {
        return new MessageSimpleService(messageMappingDAO);
    }
}
