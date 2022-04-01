package com.mqjd.web;

import org.springframework.stereotype.Component;

@Component
public class Greeter {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
