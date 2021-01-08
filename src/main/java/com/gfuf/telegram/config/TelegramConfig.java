package com.gfuf.telegram.config;

import com.gfuf.telegram.bot.AnouncerBot;
import com.gfuf.telegram.bot.receive.UpdateReceiver;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.telegram.customer.dao.service.impl.CustomerSimpleService;
import com.gfuf.telegram.customer.dao.storage.CustomerDAO;
import com.gfuf.telegram.customer.dao.storage.impl.CustomerCacheDAO;
import com.gfuf.telegram.customer.dao.storage.initer.CustomerAdminIniter;
import com.gfuf.telegram.message.service.MessageService;
import com.gfuf.telegram.message.service.impl.MessageSimpleService;
import com.gfuf.telegram.message.storage.MessageMappingDAO;
import com.gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import com.gfuf.telegram.bot.receive.converter.UpdateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfig
{
    @Bean
    public MessageService messageService(MessageMappingDAO messageMappingDAO,
                                         @Value("${telegram.chat.id}") long anouncerChatId)
    {
        return new MessageSimpleService(messageMappingDAO, anouncerChatId);
    }

    @Bean
    public UpdateReceiver updateReceiver(CustomerService customerService,
                                         UpdateConverter updateConverter,
                                         UpdateHandlersHolder updateHandlersHolder)
    {
        return new UpdateReceiver(customerService, updateConverter, updateHandlersHolder);
    }

    @Bean
    public UpdateConverter updateConverter()
    {
        return new UpdateConverter();
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

    @Bean
    public CustomerAdminIniter customerAdminIniter(@Value("${telegram.admin.user.id}") int telegram_user_id,
                                                   @Value("${telegram.admin.chat.id}") long private_chat_id,
                                                   CustomerDAO customerCacheDAO)
    {
        return new CustomerAdminIniter(telegram_user_id, private_chat_id, customerCacheDAO);
    }

    @Bean
    public AnouncerBot anouncerBot(@Value("${telegram.bot.name}") String name,
                                   @Value("${telegram.bot.token}") String token,
                                   MessageService messageService,
                                   UpdateReceiver updateReceiver)
    {
        return new AnouncerBot(name, token, messageService, updateReceiver);
    }
}
