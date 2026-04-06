package org.exemple.service;

import lombok.AllArgsConstructor;
import org.exemple.data.ProductoDto;
import org.exemple.data.command.Command;
import org.exemple.data.messaging.ReplyInbox;
import org.exemple.ports.api.ProductCommandServicePort;
import org.exemple.utils.Reply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@AllArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandServicePort {

    private final StreamBridge bridge;
    private final ReplyInbox replyInbox;
    private static final Logger logger = LoggerFactory.getLogger(ProductCommandServiceImpl.class);


    @Override
    public Reply<?> sendCreateAndAwait(ProductoDto dto, Duration timeout) throws ExecutionException, InterruptedException, TimeoutException {

        Command<ProductoDto> cmd = new Command<>("CREATE", null, dto);
        return sendAndAwait(cmd, timeout);
    }

    @Override
    public Reply<?> sendReadAndAwait(Long id, Duration timeout) {
        return null;
    }

    @Override
    public Reply<?> sendReadAllAndAwait(Duration timeout) {
        return null;
    }

    @Override
    public Reply<?> sendUpdateAndAwait(ProductoDto dto, Long id, Duration timeout) {
        return null;
    }

    @Override
    public Reply<?> sendDeleteAndAwait(Long id, Duration timeout) {
        return null;
    }

    private Reply<?> sendAndAwait(Command<?> cmd, Duration timeout) throws ExecutionException, InterruptedException, TimeoutException {
        String correlationId = UUID.randomUUID().toString();
        logger.info("Api Products Client Creating product with correlationId {}", correlationId);

        var future = replyInbox.register(correlationId);

        var msg = MessageBuilder.withPayload(cmd)
                .setHeader("correlationId", correlationId).build();

        boolean sent = this.bridge.send("commands-out-0", msg);

        if(!sent){
            throw new IllegalStateException("No se pudo enviar el comando a kafka.");
        }

        return future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
    }
}
