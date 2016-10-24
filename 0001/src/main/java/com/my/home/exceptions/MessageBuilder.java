package com.my.home.exceptions;

import com.my.home.exceptions.ui.message.AliMessage;
import com.my.home.exceptions.ui.message.AliMessageType;

/**
 *
 */
public class MessageBuilder
{

    public static AliMessage buildMessage(AliMessageType type, String message)
    {
        return buildMessage(type, message, null);
    }
    public static AliMessage buildMessage(AliMessageType type, String message, AnyException exception)
    {
        AliMessage out = new AliMessage();
        out.setType(type);
        out.setMessage(message);
        if (exception != null)
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (Exception ex : exception.getExceptions())
            {
                stringBuilder.append(ex.getMessage());
                stringBuilder.append("\n");
            }
            out.setException(stringBuilder.toString());
        }
        return out;
    }
}
