package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class DuplicateException extends ServiceException {

    public DuplicateException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.DUPLICATE.getCode();
    }

    public DuplicateException(String msg) {
        super(msg);
        super.statusCode = Status.DUPLICATE.getCode();
    }
}
