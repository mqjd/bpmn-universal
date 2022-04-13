package com.mqjd.web.handler;

import com.mqjd.web.annotation.RequestMethod;
import com.mqjd.web.annotation.RestHandler;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@RestHandler(value = "/test", method = RequestMethod.GET)
public class TestHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {
        HttpServerResponse response = context.response();
        response.end("test");
    }
}
