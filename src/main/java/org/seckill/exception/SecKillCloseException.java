package org.seckill.exception;

public class SecKillCloseException extends SecKillException {
    public SecKillCloseException(String message) {
        super(message);
    }

    public SecKillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
