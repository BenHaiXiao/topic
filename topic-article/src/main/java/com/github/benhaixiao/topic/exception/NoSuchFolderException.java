package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class NoSuchFolderException extends ServiceException {
    public NoSuchFolderException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_FOLDER.getCode();
    }

    public NoSuchFolderException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_FOLDER.getCode();
    }

    public NoSuchFolderException(){
        this("no such folder.");
    }
}
