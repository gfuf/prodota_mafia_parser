package com.gfuf.telegram.bot.receive.handler;

import com.gfuf.telegram.customer.Customer;
import com.gfuf.telegram.bot.receive.State;
import com.gfuf.telegram.domain.UpdateWrapper;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;
import java.util.List;

public interface UpdateHandler
{
    List<BotApiMethod<? extends Serializable>> handle(Customer customer, UpdateWrapper updateWrapper);

    State getState();
}
