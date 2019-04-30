package com.richard.pr.model;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;

@Log4j2
@Data
public class GreetingsResponse {

    private String greeting;

    public GreetingsResponse() {

    }

    public GreetingsResponse(String name) {
        this.withGreeting("Hello " + name + " @ " + Instant.now());
    }

    public GreetingsResponse withGreeting(String msg) {
        log.info(" name response => " + msg);
        this.greeting = msg;
        return this;
    }

}
