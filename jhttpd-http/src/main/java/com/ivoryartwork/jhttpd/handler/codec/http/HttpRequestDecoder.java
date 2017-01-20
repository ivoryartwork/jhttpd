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
        String line1 = lines[0];
        String[] line1Item = line1.split(" ");
        if (line1Item.length != 3) {
            throw new IllegalArgumentException("invalide http request");
        }
        HttpMethod httpMethod = HttpMethod.valueOf(line1Item[0]);
        String uri = line1Item[1];
        HttpVersion httpVersion = HttpVersion.valueOf(line1Item[2]);
        HttpRequest httpRequest = new DefaultHttpRequest(httpMethod, uri, httpVersion, new HttpHeaders());
        System.out.println(httpRequest.method());
        System.out.println(httpRequest.version());
        System.out.println(httpRequest.uri());

    }
}
