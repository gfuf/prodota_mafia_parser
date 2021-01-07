package gfuf.telegram.config;

import gfuf.prodota.mafia.storage.service.MafiaIsGameCustomizableService;
import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.bot.receive.handler.impl.*;
import gfuf.telegram.bot.receive.holder.UpdateHandlersHolder;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.message.service.MessageService;
import gfuf.telegram.message.service.impl.MessageSimpleService;
import gfuf.telegram.message.storage.MessageMappingDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                                                 MafiaIsGameCustomizableService mafiaIsGameCustomizableService)
    {
        return new DeleteTopicAdminHandler(customerService, updateHandlersHolder, mafiaIsGameCustomizableService);
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
