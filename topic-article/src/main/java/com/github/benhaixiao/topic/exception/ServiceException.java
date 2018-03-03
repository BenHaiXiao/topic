package com.github.benhaixiao.topic.exception;

/**
 * @author xiaobenhai
 *
 */
public class ServiceException extends RuntimeException {
    protected int statusCode = 900;
    public ServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public int getStatusCode() {
        return statusCode;
    }
}
