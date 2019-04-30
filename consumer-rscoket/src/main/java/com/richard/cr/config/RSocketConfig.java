package com.richard.cr.config;

import io.rsocket.RSocket;
import io.rsocket.RSocketFactory;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class RSocketConfig {

    @Bean
    RSocket rSocket() {
        return RSocketFactory
            .connect()
            .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .frameDecoder(PayloadDecoder.ZERO_COPY)
            .transport(TcpClientTransport.create(7000))
            .start()
            .block();
    }

    @Bean
    RSocketRequester requester(RSocketStrategies rSocketStrategies) {
        return RSocketRequester.create(this.rSocket(),
            MimeTypeUtils.APPLICATION_JSON, rSocketStrategies);
    }
}
