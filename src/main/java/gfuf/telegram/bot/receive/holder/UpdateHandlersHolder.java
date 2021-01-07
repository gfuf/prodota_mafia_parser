package gfuf.telegram.bot.receive.holder;

import gfuf.telegram.bot.receive.State;
import gfuf.telegram.bot.receive.handler.UpdateHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.dao.DuplicateKeyException;

import java.util.HashMap;
import java.util.Map;

public class UpdateHandlersHolder implements BeanPostProcessor
{
    private final Map<State, UpdateHandler> holders = new HashMap<>();


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
    {
        if (bean instanceof UpdateHandler)
        {

            UpdateHandler handler = (UpdateHandler) bean;
            if(!holders.containsKey(handler.getState()))
            {
                holders.put(handler.getState(), handler);
            }
            else
            {
                throw new IllegalStateException("Одинаковый state в разных updateHandler");
            }
        }
        return bean;
    }

    public UpdateHandler get(State state)
    {
        return holders.get(state);
    }
}
