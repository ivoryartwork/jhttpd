package com.ivoryartwork.jhttpd.handler.codec.http;

import com.ivoryartwork.jhttpd.util.CharsetUtil;

/**
 * decode http request
 *
 * @author Yaochao
 * @version 1.0
 */
public class HttpRequestDecoder {

    /**
     * decode http request by byte array
     *
     * @param bytes
     */
    public void decode(byte[] bytes) {
        String request = new String(bytes, CharsetUtil.UTF_8);
        String[] lines = request.split("\n");

        for (String line : lines) {
            System.out.println(line);
        }
    }
}
