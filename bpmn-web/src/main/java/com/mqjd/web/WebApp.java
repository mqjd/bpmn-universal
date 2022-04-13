package com.mqjd.web;

import com.mqjd.web.annotation.RestHandler;
import com.mqjd.web.util.AnnotationFilter;
import com.mqjd.web.util.ClassPathScanner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class WebApp extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        WebApp webApp = new WebApp();
        vertx.deployVerticle(webApp);
    }

    @Override
    public void start() {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        for (Class<? extends Handler<RoutingContext>> handlerClass :
                ClassPathScanner.scanAllHandlers(
                        "com.mqjd.web.handler", new AnnotationFilter(RestHandler.class))) {
            RestHandler restHandler = handlerClass.getAnnotation(RestHandler.class);
            Route route = router.route(restHandler.method().getHttpMethod(), restHandler.value());
            Handler<RoutingContext> handler = createNewHandler(handlerClass);
            if (restHandler.block()) {
                route.blockingHandler(handler);
            } else {
                route.handler(handler);
            }
        }
        router.route("/*").handler(StaticHandler.create());
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8000)
                .onSuccess(
                        server ->
                                System.out.println(
                                        "HTTP server started on port " + server.actualPort()));
    }

    private Handler<RoutingContext> createNewHandler(Class<? extends Handler<RoutingContext>> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("create new handler error", e);
        }
    }
}
