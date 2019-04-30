# RSocket Messaging in Spring Boot 2.2

## Endpoints: 

### Simple request:
<pre> <code>
    // Project consumer -> class GreetingsResource.
    @GetMapping("/greet/{name}")
        Publisher<GreetingsResponse> greet(@PathVariable String name) {
        return this.requester
            .route("greet")
            .data(new GreetingsRequest(name))
            .retrieveMono(GreetingsResponse.class);
    }
</code> </pre>

<pre> <code>
    // Project producer -> class GreetingsController.
    @MessageMapping("greet")
    GreetingsResponse greet(GreetingsRequest request) {
        log.info(" name request => " + request.getName());
        return new GreetingsResponse(request.getName());
    }
</code> </pre>

#### Result:
![Simple request.](data/simple-request.png) 

### Stream request.
<pre> <code>
    // Project consumer -> class GreetingsResource
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/greet/sse/{name}")
    Publisher<GreetingsResponse> greetStream(@PathVariable String name) {

        return this.requester
            .route("greet-stream")
            .data(new GreetingsRequest(name))
            .retrieveFlux(GreetingsResponse.class);
    }
</code> </pre>

<pre> <code>
    // Project producer -> class GreetingsController.
    @MessageMapping("greet-stream")
    Flux<GreetingsResponse> greetStream(GreetingsRequest request) {
        log.info(" name request stream => " + request.getName());
        return Flux
         .fromStream(Stream.generate(() -> new GreetingsResponse(request.getName())))
         .delayElements(Duration.ofSeconds(1));
    }
</code> </pre>

#### Result:
![Stream request.](data/request-stream.png)

### Request stream with treated error, using `@MessageExceptionHandler`
<pre> <code>
   // Project consumer -> class GreetingsResource
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
</code> </pre>

<pre> <code>
    // Project producer -> class GreetingsController.
    @MessageMapping("error")
    Flux<GreetingsResponse> error() {
        return Flux.error(new IllegalArgumentException());
    }

    @MessageExceptionHandler
    Flux<GreetingsResponse> errorHandler(IllegalArgumentException iae) {
        return Flux.just(new GreetingsResponse()
            .withGreeting("OH NO !"));

    }
</code> </pre>

#### Result:
![Request stream with treated error.](data/request-stream-error.png)
