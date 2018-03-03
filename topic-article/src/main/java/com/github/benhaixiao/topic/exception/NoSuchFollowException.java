package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 */
public class NoSuchFollowException extends ServiceException {
    public NoSuchFollowException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_FOLLOW.getCode();
    }

    public NoSuchFollowException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_FOLLOW.getCode();
    }

    public NoSuchFollowException(){
        this("no such follow.");
    }
}
