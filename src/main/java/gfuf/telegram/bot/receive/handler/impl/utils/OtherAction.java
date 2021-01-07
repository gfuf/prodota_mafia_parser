package gfuf.telegram.bot.receive.handler.impl.utils;

import java.util.Objects;

import static gfuf.telegram.bot.receive.handler.impl.utils.ActionConstants.CANCEL_COMMAND;
import static gfuf.telegram.bot.receive.handler.impl.utils.ActionConstants.CANCEL_TEXT;

public enum OtherAction implements KeyAction
{
    CANCEL(CANCEL_TEXT, CANCEL_COMMAND);

    private final String text;

    private final String command;

    OtherAction(String text, String command)
    {
        this.text = text;
        this.command = command;
    }

    public String getText()
    {
        return text;
    }

    public String getCommand()
    {
        return command;
    }

    public boolean isThisCommand(String command)
    {
        return Objects.equals(command, this.command);
    }
}
