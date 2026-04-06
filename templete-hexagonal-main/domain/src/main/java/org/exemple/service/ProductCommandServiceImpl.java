package org.exemple.service;

import lombok.AllArgsConstructor;
import org.exemple.data.ProductoDto;
import org.exemple.ports.api.ProductCommandServicePort;
import org.exemple.utils.Reply;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@AllArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandServicePort {



    @Override
    public Reply<?> sendCreateAndAwait(ProductoDto dto, Duration timeout) throws ExecutionException, InterruptedException, TimeoutException {
        return null;
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
}
