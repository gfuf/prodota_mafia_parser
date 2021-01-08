package com.gfuf.web.response;

import java.util.Objects;

public class ResponseDecorator<T>
{
    private T result;

    private Exception error;

    public ResponseDecorator(T result)
    {
        this(result, null);
    }

    public ResponseDecorator(T result, Exception error)
    {
        this.result = result;
        this.error = error;
    }

    public ResponseDecorator(Exception error)
    {
        this(null, error);
    }

    public T getResult()
    {
        return result;
    }

    public Exception getError()
    {
        return error;
    }

    public boolean hasResponse()
    {
        return Objects.nonNull(result);
    }

    public boolean hasError()
    {
        return Objects.nonNull(error);
    }


}
