package com.mqjd.web.annotation;

import io.vertx.core.http.HttpMethod;

public enum RequestMethod {
    GET(HttpMethod.GET),
    HEAD(HttpMethod.HEAD),
    POST(HttpMethod.POST),
    PUT(HttpMethod.PUT),
    PATCH(HttpMethod.PATCH),
    DELETE(HttpMethod.DELETE),
    OPTIONS(HttpMethod.OPTIONS),
    TRACE(HttpMethod.TRACE);

    private final HttpMethod httpMethod;

    RequestMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
