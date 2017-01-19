package com.ivoryartwork.jhttpd.handler.codec.http;

import com.ivoryartwork.jhttpd.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * http method
 *
 * @author Yaochao
 * @version 1.0
 */
public class HttpMethod implements Comparable<HttpMethod> {

    /**
     * http options method
     */
    public static final HttpMethod OPTIONS = new HttpMethod("OPTIONS");

    /**
     * http get method
     */
    public static final HttpMethod GET = new HttpMethod("GET");

    /**
     * http head method
     */
    public static final HttpMethod HEAD = new HttpMethod("HEAD");

    /**
     * http put method
     */
    public static final HttpMethod PUT = new HttpMethod("PUT");

    /**
     * http post method
     */
    public static final HttpMethod POST = new HttpMethod("POST");

    /**
     * http trace method
     */
    public static final HttpMethod TRACE = new HttpMethod("TRACE");

    /**
     * http delete method
     */
    public static final HttpMethod DELETE = new HttpMethod("DELETE");

    private static final Map<String, HttpMethod> methodMap = new HashMap<String, HttpMethod>();

    private final String name;

    static {
        methodMap.put(OPTIONS.toString(), OPTIONS);
        methodMap.put(GET.toString(), GET);
        methodMap.put(HEAD.toString(), HEAD);
        methodMap.put(POST.toString(), POST);
        methodMap.put(PUT.toString(), PUT);
        methodMap.put(DELETE.toString(), DELETE);
        methodMap.put(TRACE.toString(), TRACE);
    }

    public HttpMethod(String name) {
        name = Assert.checkNotNull(name, "name").trim();
        Assert.checkEmpty(name, "empty name");
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isISOControl(c) || Character.isWhitespace(c)) {
                throw new IllegalArgumentException("invalid character in name");
            }
        }
        this.name = name;
    }

    /**
     * Returns the {@link HttpMethod} represented by the specified name.
     * If the specified name is a standard HTTP method name, a cached instance
     * will be returned.  Otherwise, a new instance will be returned.
     */
    public static HttpMethod valueOf(String name) {
        HttpMethod result = methodMap.get(name);
        return result == null ? new HttpMethod(name) : result;
    }

    /**
     * get http method name
     *
     * @return
     */
    public String name() {
        return name;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HttpMethod)) {
            return false;
        }
        HttpMethod m = (HttpMethod) obj;
        return name().equals(m.name());
    }

    @Override
    public int compareTo(HttpMethod o) {
        return name().compareTo(o.name());
    }
}
