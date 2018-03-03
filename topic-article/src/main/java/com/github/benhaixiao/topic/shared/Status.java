package com.github.benhaixiao.topic.shared;

/**
 * @author xiaobenhai
 *
 */
public enum Status {
    ILLEGAL_PARAMETER(199,"illegal parameter."),
    SUCCESS(200,"success"),
    DUPLICATE(201,"duplicate error."),
    JSON_DESERIALIZE_ERROR(202,"json deserialize error."),
    NO_SUCH_APP(203,"no such app"),
    NO_SUCH_ARTICLE(206,"no such article"),
    NO_SUCH_FOLDER(204,"no such folder"),
    NO_SUCH_NODE_META(205,"no such meta"),
    NO_SUCH_FOLLOW(207,"no such follow"),
    NO_SUCH_ACCLAIM(208,"no such acclaim"),
    NO_SUCH_POST(209,"no such post"),
    NO_SUCH_PERMISSION(250,"no such permission"),
    No_SUCH_ROLE(251,"no such role");


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
