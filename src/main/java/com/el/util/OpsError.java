package com.el.util;

/**
 * @author danfeng
 * @since 2018/2/7.
 */
public class OpsError extends Error {
    public OpsError(String message) {
        super(message);
    }

    public OpsError(String message, Throwable cause) {
        super(message, cause);
    }
}
