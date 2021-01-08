package com.gfuf.utils;

public class ProdotaRuntimeException extends RuntimeException
{
    public ProdotaRuntimeException()
    {
        super();
    }
    public ProdotaRuntimeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProdotaRuntimeException(Throwable cause)
    {
        super(cause);
    }
}
