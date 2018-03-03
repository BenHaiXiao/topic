package com.github.benhaixiao.topic.web.exception;

/**
 * @author xiaobenhai
 *
 */
public class FileUploadException extends RuntimeException {
    public FileUploadException(String msg, Throwable t) {
        super(msg, t);
    }

    public FileUploadException(String msg) {
        super(msg);
    }
}