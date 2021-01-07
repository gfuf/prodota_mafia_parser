package gfuf.telegram.bot.receive.handler;

import gfuf.telegram.bot.receive.State;
import gfuf.telegram.customer.Customer;
import gfuf.telegram.customer.dao.service.CustomerService;
import gfuf.telegram.domain.UpdateWrapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface UpdateHandler
{
    List<SendMessage> handle(Customer customer, UpdateWrapper updateWrapper);

    State getState();
}
