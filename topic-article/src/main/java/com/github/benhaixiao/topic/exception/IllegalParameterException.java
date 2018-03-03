package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class IllegalParameterException extends ServiceException {

    public IllegalParameterException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.ILLEGAL_PARAMETER.getCode();
    }

    public IllegalParameterException(String msg) {
        super(msg);
        super.statusCode = Status.ILLEGAL_PARAMETER.getCode();
    }
}
