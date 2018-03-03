package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class NoSuchNodeMetaException extends ServiceException {
    public NoSuchNodeMetaException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_NODE_META.getCode();
    }

    public NoSuchNodeMetaException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_NODE_META.getCode();
    }

    public NoSuchNodeMetaException(){
        this("no such node meta.");
    }
}
