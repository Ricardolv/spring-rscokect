package com.richard.pr.controller;

import com.richard.pr.model.GreetingsRequest;
import com.richard.pr.model.GreetingsResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Log4j2
@Controller
public class GreetingsController {

    @MessageMapping("error")
    Flux<GreetingsResponse> error() {
        return Flux.error(new IllegalArgumentException());
    }

    @MessageExceptionHandler
    Flux<GreetingsResponse> errorHandler(IllegalArgumentException iae) {
        return Flux.just(new GreetingsResponse()
            .withGreeting("OH NO !"));

    }

    @MessageMapping("greet-stream")
    Flux<GreetingsResponse> greetStream(GreetingsRequest request) {
        log.info(" name request stream => " + request.getName());
        return Flux
         .fromStream(Stream.generate(() -> new GreetingsResponse(request.getName())))
         .delayElements(Duration.ofSeconds(1));
    }

    @MessageMapping("greet")
    GreetingsResponse greet(GreetingsRequest request) {
        log.info(" name request => " + request.getName());
        return new GreetingsResponse(request.getName());
    }
}
