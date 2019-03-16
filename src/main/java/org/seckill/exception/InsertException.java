package org.seckill.exception;

public class InsertException extends SecKillException {
    public InsertException(String message) {
        super(message);
    }

    public InsertException(String message, Throwable cause) {
        super(message, cause);
    }
}
