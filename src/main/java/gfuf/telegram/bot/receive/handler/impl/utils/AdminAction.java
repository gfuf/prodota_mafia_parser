package gfuf.telegram.bot.receive.handler.impl.utils;

import gfuf.telegram.bot.receive.State;

import java.util.Objects;

import static gfuf.telegram.bot.receive.handler.impl.utils.ActionConstants.*;

public enum AdminAction implements KeyAction
{
    DELETE_TOPIC(DELETE_TOPIC_TEXT, DELETE_TOPIC_COMMAND, State.DELETE_TOPIC_ADMIN, DELETE_TOPIC_MESSAGE_TEXT),
    ADD_TOPIC(ADD_TOPIC_TEXT, ADD_TOPIC_COMMAND, State.ADD_TOPIC_ADMIN, ADD_TOPIC_MESSAGE_TEXT),
    ADD_ADMIN(ADD_ADMIN_TEXT, ADD_ADMIN_COMMAND, State.ADD_ADMIN_ADMIN, ADD_ADMIN_MESSAGE_TEXT),
    WRITE_REPORT(WRITE_REPORT_TEXT, WRITE_REPORT_COMMAND, State.WRITE_REPORT_ADMIN, WRITE_REPORT_MESSAGE_TEXT);

    private final String text;

    private final String command;

    private final State nextState;

    private final String messageText;

    AdminAction(String text, String command, State nextState, String messageText)
    {
        this.text = text;
        this.command = command;
        this.nextState = nextState;
        this.messageText = messageText;
    }

    public String getText()
    {
        return text;
    }

    public String getCommand()
    {
        return command;
    }

    public State getNextState()
    {
        return nextState;
    }

    public String getMessageText()
    {
        return messageText;
    }

    public static final AdminAction fromCommand(String command)
    {
        for (AdminAction adminAction : values())
        {
            if(Objects.equals(adminAction.command, command))
            {
                return adminAction;
            }
        }

        return  null;
    }
}
