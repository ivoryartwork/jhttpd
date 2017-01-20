package com.ivoryartwork.jhttpd.handler.codec.http;

import com.ivoryartwork.jhttpd.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yaochao
 * @version 1.0
 */
public class HttpVersion implements Comparable<HttpVersion> {

    public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP", 1, 0, false);

    public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP", 1, 1, true);

    private static final String HTTP_1_0_STR = "HTTP/1.0";
    private static final String HTTP_1_1_STR = "HTTP/1.1";
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+).(\\d)");

    private final String protocolName;
    private final int majorVersion;
    private final int minorVersion;
    private final boolean keepAliveDefault;
    private final String text;

    public HttpVersion(String protocolName, int majorVersion, int minorVersion, boolean keepAliveDefault) {
        protocolName = Assert.checkNotNull(protocolName, "protocolName");
        protocolName = protocolName.trim().toUpperCase();
        protocolName = Assert.checkEmpty(protocolName, "empty protocolName");

        for (int i = 0; i < protocolName.length(); i++) {
            char c = protocolName.charAt(i);
            if (Character.isISOControl(c) || Character.isWhitespace(c)) {
                throw new IllegalArgumentException("invalid character in name");
            }
        }

        if (majorVersion < 0) {
            throw new IllegalArgumentException("negative majorVersion");
        }
        if (minorVersion < 0) {
            throw new IllegalArgumentException("negative minorVersion");
        }
        this.protocolName = protocolName;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.keepAliveDefault = keepAliveDefault;
        this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
    }

    public HttpVersion(String text, boolean keepAliveDefault) {
        text = Assert.checkNotNull(text, "text");
        text = Assert.checkEmpty(text, "empty text");
        text = text.trim().toUpperCase();
        Matcher matcher = VERSION_PATTERN.matcher(text);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("invalid version format: " + text);
        }
        protocolName = matcher.group(1);
        majorVersion = Integer.parseInt(matcher.group(2));
        minorVersion = Integer.parseInt(matcher.group(3));
        this.text = protocolName + '/' + majorVersion + '.' + minorVersion;
        this.keepAliveDefault = keepAliveDefault;
    }

    public static HttpVersion valueOf(String text) {
        text = Assert.checkNotNull(text, "text").trim();
        text = Assert.checkEmpty(text, "empty text");
        if (text.equals(HTTP_1_0_STR)) {
            return HTTP_1_0;
        } else if (text.equals(HTTP_1_1)) {
            return HTTP_1_1;
        } else {
            return new HttpVersion(text, true);
        }
    }

    public String protocolName() {
        return protocolName;
    }

    public int majorVersion() {
        return majorVersion;
    }

    public int minorVersion() {
        return minorVersion;
    }

    public boolean isKeepAliveDefault() {
        return keepAliveDefault;
    }

    public String text() {
        return text;
    }

    @Override
    public String toString() {
        return text();
    }

    @Override
    public int hashCode() {
        return (protocolName().hashCode() * 31 + majorVersion()) * 31 +
                minorVersion();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HttpVersion)) {
            return false;
        }
        HttpVersion that = (HttpVersion) obj;
        return majorVersion() == that.majorVersion() &&
                minorVersion() == that.minorVersion() &&
                protocolName().equals(that.protocolName());
    }

    @Override
    public int compareTo(HttpVersion o) {
        int v = protocolName().compareTo(o.protocolName());
        if (v != 0) {
            return v;
        }

        v = majorVersion() - o.majorVersion();
        if (v != 0) {
            return v;
        }

        return minorVersion() - o.minorVersion();
    }
}
