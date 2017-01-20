package com.ivoryartwork.jhttpd.handler.codec.http;

import com.ivoryartwork.jhttpd.util.Assert;

/**
 * @author Yaochao
 * @version 1.0
 */
public class DefaultHttpRequest implements HttpRequest {

    private final HttpMethod method;
    private final String uri;
    private final HttpVersion version;
    private final HttpHeaders headers;

    public DefaultHttpRequest(HttpMethod method, String uri, HttpVersion version, HttpHeaders headers) {
        method = Assert.checkNotNull(method, "http method");
        uri = Assert.checkNotNull(uri, "uri").trim();
        version = Assert.checkNotNull(version, "http version");
        headers = Assert.checkNotNull(headers, "http headers");
        this.method = method;
        this.uri = uri;
        this.version = version;
        this.headers = headers;
    }

    @Override
    public HttpMethod method() {
        return method;
    }

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public HttpHeaders headers() {
        return headers;
    }

    @Override
    public HttpVersion version() {
        return version;
    }
}
