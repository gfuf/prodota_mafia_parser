package gfuf.telegram.config;

import gfuf.telegram.bot.receive.UpdateReceiver;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.customer.dao.service.impl.CustomerSimpleService;
import gfuf.telegram.customer.dao.storage.CustomerDAO;
import gfuf.telegram.customer.dao.storage.impl.CustomerCacheDAO;
import gfuf.telegram.message.service.MessageService;
import gfuf.telegram.message.service.impl.MessageSimpleService;
import gfuf.telegram.message.storage.MessageMappingDAO;
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

    @Bean
    public UpdateReceiver updateReceiver(CustomerService customerService)
    {
        return new UpdateReceiver(customerService);
    }

    @Bean
    public CustomerService customerService(CustomerDAO customerCacheDAO)
    {
        return new CustomerSimpleService(customerCacheDAO);
    }

    @Bean
    public CustomerDAO customerCacheDAO(CustomerDAO customerPostgresDAO)
    {
        return new CustomerCacheDAO(customerPostgresDAO);
    }
}
