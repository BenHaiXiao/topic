package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 */

public class NoSuchAcclaimException extends ServiceException {
    public NoSuchAcclaimException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_ACCLAIM.getCode();
    }

    public NoSuchAcclaimException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_ACCLAIM.getCode();
    }

    public NoSuchAcclaimException(){
        this("no such acclaim.");
    }
}
