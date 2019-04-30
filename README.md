# RSocket Messaging in Spring Boot 2.2

## Consumer endpoints: 

### Simple request.

<p> Project consumer.
<pre> <code>
    @GetMapping("/greet/{name}")
        Publisher<GreetingsResponse> greet(@PathVariable String name) {
        return this.requester
            .route("greet")
            .data(new GreetingsRequest(name))
            .retrieveMono(GreetingsResponse.class);
    }
</code> </pre>
</p>

<p> Project producer.
<pre> <code>
    @MessageMapping("greet")
    GreetingsResponse greet(GreetingsRequest request) {
        log.info(" name request => " + request.getName());
        return new GreetingsResponse(request.getName());
    }
</code> </pre>
</p>

![Simple request.](data/simple-request.png) 

### Stream request.
![Stream request.](data/request-stream.png)

### Request stream with treated error, using `@MessageExceptionHandler`
![Request stream with treated error.](data/request-stream-error.png)
