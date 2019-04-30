package com.richard.cr.resources;

import com.richard.cr.model.GreetingsRequest;
import com.richard.cr.model.GreetingsResponse;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GreetingsResource {

    private final RSocketRequester requester;

    public GreetingsResource(RSocketRequester requester) {
        this.requester = requester;
    }

    @GetMapping("/greet/error")
    Publisher<GreetingsResponse> error() {
        return this.requester
            .route("error")
            .data(Mono.empty())
            .retrieveFlux(GreetingsResponse.class);
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/greet/sse/{name}")
    Publisher<GreetingsResponse> greetStream(@PathVariable String name) {

        return this.requester
            .route("greet-stream")
            .data(new GreetingsRequest(name))
            .retrieveFlux(GreetingsResponse.class);
    }

    @GetMapping("/greet/{name}")
    Publisher<GreetingsResponse> greet(@PathVariable String name) {

        return this.requester
            .route("greet")
            .data(new GreetingsRequest(name))
            .retrieveMono(GreetingsResponse.class);
    }


}
