package gfuf.telegram.bot;

import gfuf.telegram.bot.receive.handler.UpdateHandler;
import gfuf.telegram.bot.receive.handler.impl.EnterTextDefaultHandler;
import gfuf.telegram.bot.receive.handler.impl.NoneHandler;
import gfuf.telegram.bot.receive.handler.impl.StartAdminHandler;
import gfuf.telegram.bot.receive.handler.impl.StartDefaultUpdateHandler;

public enum State
{
    NONE( new NoneHandler()),
    START_DEFAULT( new StartDefaultUpdateHandler()),
    ENTER_TEXT_DEFAULT(new EnterTextDefaultHandler()),
    START_ADMIN(new StartAdminHandler());

    private final UpdateHandler handler;

    State(UpdateHandler handler)
    {
        this.handler = handler;
    }

    public UpdateHandler getHandler()
    {
        return handler;
    }
}
