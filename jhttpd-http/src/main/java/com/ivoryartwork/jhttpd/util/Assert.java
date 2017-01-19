package com.ivoryartwork.jhttpd.util;

/**
 * @author Yaochao
 * @version 1.0
 */
public final class Assert {

    /**
     * check the object is not null
     *
     * @param arg
     * @param text
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T arg, String text) {
        if (arg == null) {
            throw new NullPointerException(text);
        }
        return arg;
    }

    /**
     * check the string is not null
     *
     * @param arg
     * @param text
     * @return
     */
    public static String checkEmpty(String arg, String text) {
        if (arg.isEmpty()) {
            throw new IllegalArgumentException(text);
        }
        return arg;
    }
}
