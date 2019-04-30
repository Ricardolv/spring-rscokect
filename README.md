# RSocket Messaging in Spring Boot 2.2

## Consumer endpoints: 

### Simple request.

#### Project consumer.
`@GetMapping("/greet/{name}")
    Publisher<GreetingsResponse> greet(@PathVariable String name) {
        return this.requester
            .route("greet")
            .data(new GreetingsRequest(name))
            .retrieveMono(GreetingsResponse.class);
}`

#### Project producer.
`
    @MessageMapping("greet")
    GreetingsResponse greet(GreetingsRequest request) {
        log.info(" name request => " + request.getName());
        return new GreetingsResponse(request.getName());
    }
`

![Simple request.](data/simple-request.png) 

### Stream request.
![Stream request.](data/request-stream.png)

### Request stream with treated error, using `@MessageExceptionHandler`
![Request stream with treated error.](data/request-stream-error.png)
