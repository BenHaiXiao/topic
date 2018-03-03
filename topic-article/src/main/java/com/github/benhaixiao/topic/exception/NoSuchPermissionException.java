package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhaion
 */
public class NoSuchPermissionException extends ServiceException {
    public NoSuchPermissionException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_PERMISSION.getCode();
    }

    public NoSuchPermissionException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_PERMISSION.getCode();
    }

    public NoSuchPermissionException(){
        this("no such permission.");
    }
}
