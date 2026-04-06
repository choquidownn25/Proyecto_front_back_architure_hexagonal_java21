package org.exemple.ports.api;

import org.exemple.data.ProductoDto;
import org.exemple.utils.Reply;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface ProductCommandServicePort {
    Reply<?> sendCreateAndAwait(ProductoDto dto, Duration timeout) throws ExecutionException, InterruptedException, TimeoutException;
    Reply<?> sendReadAndAwait(Long id, Duration timeout);
    Reply<?> sendReadAllAndAwait(Duration timeout);
    Reply<?> sendUpdateAndAwait(ProductoDto dto, Long id, Duration timeout);
    Reply<?> sendDeleteAndAwait(Long id, Duration timeout);

}
