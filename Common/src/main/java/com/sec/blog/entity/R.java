package com.sec.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = -4596392194078121504L;

    private int code;
    private T data;
    private String msg;

    public static <T> R<T> ok() {
        return new R<T>(200, null, "");
    }

    public static <T> R<T> ok(T data) {
        return new R<T>(200, data, "");
    }

    public static <T> R<T> ok(T data, String msg) {
        return new R<T>(200, data, msg);
    }

    public static <T> R<T> error(String msg) {
        return new R<T>(500, null, msg);
    }

    public static <T> R<T> error(T data, String msg) {
        return new R<T>(500, data, msg);
    }

}
