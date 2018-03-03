package com.github.benhaixiao.topic.exception;

import com.github.benhaixiao.topic.shared.Status;

/**
 * @author xiaobenhai
 *
 */
public class JacksonDeserializeException extends ServiceException {
    public JacksonDeserializeException(String msg, Throwable t) {
        super(msg, t);
        super.statusCode = Status.JSON_DESERIALIZE_ERROR.getCode();
    }

    public JacksonDeserializeException(String msg) {
        super(msg);
        super.statusCode = Status.JSON_DESERIALIZE_ERROR.getCode();
    }
}
