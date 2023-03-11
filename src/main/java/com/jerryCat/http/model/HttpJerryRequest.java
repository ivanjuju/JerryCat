package com.jerryCat.http.model;

import com.jerryCat.net.JerryRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.Map;

/**
 * @author ivanzhu
 */
public class HttpJerryRequest implements JerryRequest {
    private final HttpRequest httpRequest;

    public HttpJerryRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getUri() {
        return httpRequest.uri();
    }

    public String getPath() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.path();
    }

    public String getMethod() {
        return httpRequest.method().name();
    }

    public Map<String, List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());
        return decoder.parameters();
    }

    public List<String> getParameters(String name) {
        return getParameters().get(name);
    }

    public String getParameter(String name) {
        List<String> strings = getParameters().get(name);
        if (strings == null || strings.size() == 0) {
            return StringUtil.EMPTY_STRING;
        }
        return strings.get(0);
    }

    public boolean isKeepAlive() {
        return HttpUtil.isKeepAlive(httpRequest);
    }
}
