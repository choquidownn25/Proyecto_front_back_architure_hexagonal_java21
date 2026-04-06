package org.exemple.handlers;


import lombok.AllArgsConstructor;
import org.exemple.data.messaging.ReplyInbox;
import org.exemple.utils.Reply;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@AllArgsConstructor
public class RepliesConsumer {

    private final ReplyInbox replyInbox;

    @Bean
    public Consumer<Message<Reply<?>>> handleReplies() {
        return message -> {
            String correlationId = message.getHeaders().get("correlationId", String.class);
            replyInbox.complete(correlationId, message.getPayload());
        };
    }
}
