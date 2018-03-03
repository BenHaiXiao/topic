package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class NoSuchArticleException extends ServiceException {
    public NoSuchArticleException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.NO_SUCH_ARTICLE.getCode();
    }

    public NoSuchArticleException(String msg) {
        super(msg);
        super.statusCode = Status.NO_SUCH_ARTICLE.getCode();
    }

    public NoSuchArticleException(){
        this("no such article.");
    }
}
