package org.example.configuration;

import org.example.adapters.ProductoJpaAdapter;
import org.exemple.data.messaging.ReplyInbox;
import org.exemple.ports.api.ProductCommandServicePort;
import org.exemple.ports.api.ProductoServicePort;
import org.exemple.ports.spi.ProductoPersistencePort;
import org.exemple.service.ProductCommandServiceImpl;
import org.exemple.service.ProductoServiceImpl;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductoConfig {
    @Bean
    public ProductoPersistencePort productoPersistence(){
        return new ProductoJpaAdapter() ;
    }

    @Bean
    public ProductoServicePort productoService(){
        return new ProductoServiceImpl(productoPersistence());
    }

    @Bean
    public ProductCommandServicePort productCommandService(StreamBridge streamBridge, ReplyInbox replyInbox) {
        return new ProductCommandServiceImpl( streamBridge, replyInbox);
    }

    @Bean
    public ReplyInbox replyInbox() {
        return new ReplyInbox();
    }
}
