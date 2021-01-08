package com.gfuf.telegram.config;

import com.gfuf.telegram.bot.receive.handler.impl.*;
import com.gfuf.telegram.customer.dao.service.CustomerService;
import com.gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import com.gfuf.prodota.mafia.storage.service.MafiaStorageService;
import com.gfuf.telegram.bot.receive.handler.UpdateHandler;
import com.gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import com.gfuf.telegram.message.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlersConfig
{
    @Bean
    public UpdateHandler actionAdminHandler(CustomerService customerService, UpdateHandlersHolder updateHandlersHolder)
    {
        return new ActionAdminHandler(customerService, updateHandlersHolder);
    }

    @Bean
    public UpdateHandler addAdminAdminHandler(CustomerService customerService,
                                              UpdateHandlersHolder updateHandlersHolder)
    {
        return new AddAdminAdminHandler(customerService, updateHandlersHolder);
    }

    @Bean
    public UpdateHandler addTopicAdminHandler(CustomerService customerService,
                                              UpdateHandlersHolder updateHandlersHolder,
                                              MafiaIsGameCustomizableService mafiaIsGameCustomizableService)
    {
        return new AddTopicAdminHandler(customerService, updateHandlersHolder, mafiaIsGameCustomizableService);
    }

    @Bean
    public UpdateHandler deleteTopicAdminHandler(CustomerService customerService,
                                                 UpdateHandlersHolder updateHandlersHolder,
                                                 MafiaIsGameCustomizableService mafiaIsGameCustomizableService,
                                                 MafiaStorageService mafiaStorageService,
                                                 MessageService messageService)
    {
        return new DeleteTopicAdminHandler(customerService, updateHandlersHolder, mafiaIsGameCustomizableService,
                mafiaStorageService, messageService);
    }

    @Bean
    public UpdateHandler startAdminHandler(CustomerService customerService)
    {
        return new StartAdminHandler(customerService);
    }

    @Bean
    public UpdateHandler startDefaultUpdateHandler(CustomerService customerService)
    {
        return new StartDefaultUpdateHandler(customerService);
    }

    @Bean
    public UpdateHandler writeReportAdminHandler(CustomerService customerService,
                                                 UpdateHandlersHolder updateHandlersHolder)
    {
        return new WriteReportAdminHandler(customerService, updateHandlersHolder);
    }

    @Bean
    public UpdateHandler writeReportDefaultHandler(CustomerService customerService)
    {
        return new WriteReportDefaultHandler(customerService);
    }

    @Bean
    public UpdateHandlersHolder updateHandlersHolder()
    {
        return new UpdateHandlersHolder();
    }
}
