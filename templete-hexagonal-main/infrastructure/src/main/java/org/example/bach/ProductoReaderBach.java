package org.example.bach;

import lombok.RequiredArgsConstructor;
import org.example.entity.Producto;
import org.example.repository.ProductoRepository;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ProductoReaderBach {
    private final ProductoRepository productoRepository;

}
