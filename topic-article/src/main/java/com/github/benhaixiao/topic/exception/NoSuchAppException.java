package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class NoSuchAppException extends ServiceException{
    public NoSuchAppException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_APP.getCode();
    }

    public NoSuchAppException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_APP.getCode();
    }

    public NoSuchAppException(){
        this("no such app.");
    }
}
