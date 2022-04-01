package com.mqjd.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class GreetingVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(GreetingVerticle.class);

    @Autowired Greeter greeter;

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.createHttpServer()
                .requestHandler(
                        request -> {
                            String name = request.getParam("name");
                            LOG.info("Got request for name: " + name);
                            if (name == null) {
                                request.response().setStatusCode(400).end("Missing name");
                            } else {
                                // It's fine to call the greeter from the event loop as it's not
                                // blocking
                                request.response().end(greeter.sayHello(name));
                            }
                        })
                .listen(
                        8080,
                        ar -> {
                            if (ar.succeeded()) {
                                LOG.info("GreetingVerticle started: @" + this.hashCode());
                                startPromise.complete();
                            } else {
                                startPromise.fail(ar.cause());
                            }
                        });
    }
}
