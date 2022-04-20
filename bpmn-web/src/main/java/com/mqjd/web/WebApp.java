package com.mqjd.web;

import com.mqjd.web.annotation.RestHandler;
import com.mqjd.web.util.AnnotationFilter;
import com.mqjd.web.util.ClassPathScanner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.jdbcclient.JDBCPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.Row;

import java.util.List;

public class WebApp extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        WebApp webApp = new WebApp();
        vertx.deployVerticle(webApp);
    }

    @Override
    public void start() {
        Pool pool = JDBCPool.pool(vertx, new JsonObject().put("url", "jdbc:sqlite:bpmn.db"));
        pool.query("create table if not exits test(id int primary key, name varchar(255))").execute()
            .compose(r -> pool.query("insert into test values (1, 'Hello'), (2, 'World')").execute())
            .compose(r -> pool.query("select * " + "from test").execute().onSuccess(rows -> {
                for (Row row : rows) {
                    System.out.println(row.toJson());
                }
            })).onFailure(Throwable::printStackTrace);
        Router router = Router.router(vertx);
        List<? extends Class<? extends Handler<RoutingContext>>> handlers = ClassPathScanner.scanAllHandlers(
                "com.mqjd.web.handler", new AnnotationFilter(RestHandler.class));
        for (Class<? extends Handler<RoutingContext>> handlerClass : handlers) {
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
        vertx.createHttpServer().requestHandler(router).listen(8000)
             .onSuccess(server -> System.out.println("HTTP " + "server started on port " + server.actualPort()));
    }

    private Handler<RoutingContext> createNewHandler(Class<? extends Handler<RoutingContext>> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("create new handler error", e);
        }
    }
}
