package com.ivoryartwork.jhttpd.handler.codec.http;

/**
 * http request
 *
 * @author Yaochao
 * @version 1.0
 */
public interface HttpRequest {

    /**
     * get http method
     *
     * @return
     */
    HttpMethod method();

    /**
     * get uri
     *
     * @return
     */
    String Uri();


}