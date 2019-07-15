package com.el.util;

/**
 * @author danfeng
 * @since 2018/2/6.
 */
public class DevError extends Error {
    public DevError(String message) {
        super(message);
    }

    public DevError(String message, Throwable cause) {
        super(message, cause);
    }

    public static DevError todo() {
        return new DevError("Not impl.");
    }

    public static DevError unexpected() {
        return unexpected("Unexpected.");
    }

    public static DevError unexpected(String message) {
        return new DevError(message);
    }
}
