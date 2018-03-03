package com.github.benhaixiao.topic.util;

/**
 * @author xiaobenhai
 *
 */
public enum Status {
    JSON_DESERIALIZE_ERROR(202,"json deserialize error."),
    NO_SUCH_PERMISSION(250,"no such permission"),
    NO_SUCH_ROLE(251,"no such role"),
    NO_SUCH_USER_ROLE(252,"no such user_role"),
    NO_SUCHU_USER(253,"no such user");

    private int code;
    private String message;

    private Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
