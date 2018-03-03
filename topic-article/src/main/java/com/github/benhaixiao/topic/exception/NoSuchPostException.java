package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class NoSuchPostException extends ServiceException {
    public NoSuchPostException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_POST.getCode();
    }

    public NoSuchPostException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_POST.getCode();
    }

    public NoSuchPostException(){
        this("no such post.");
    }
}
