package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.exemple.data.ProductoDto;
import org.exemple.data.response.ProductoDtoResponse;
import org.exemple.ports.api.ProductCommandServicePort;
import org.exemple.ports.api.ProductoServicePort;
import org.exemple.utils.Reply;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.jspecify.annotations.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.springframework.cloud.stream.binder.kafka.properties.KafkaConsumerProperties.StandardHeaders.id;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*", maxAge = 3600)

public class ProductoController {
    @Autowired
    private ProductoServicePort productoServicePort;
    @Autowired
    private ProductCommandServicePort productCommandServicePort;

    @PostMapping("/add")
    public ResponseEntity<ProductoDtoResponse> addProducto(@RequestBody ProductoDto productoDto) {
        ProductoDtoResponse productoDtoResponse = new ProductoDtoResponse();
        productoDtoResponse = productoServicePort.addProductoDto(productoDto);
        if (productoDtoResponse != null)
            return ResponseEntity.ok(productoDtoResponse);
        else
            return ResponseEntity.badRequest().build();

    }

    @PostMapping("/update")
    public ResponseEntity<ProductoDtoResponse>  updateProducto(@RequestBody ProductoDto productoDto) {
        ProductoDtoResponse productoDtoResponse = new ProductoDtoResponse();
        productoDtoResponse = productoServicePort.updateProductoDto(productoDto);
        if (productoDtoResponse != null)
            return  ResponseEntity.ok(productoDtoResponse );
        else
            return  ResponseEntity.badRequest().build();

    }
    @GetMapping("/get")
    public  ResponseEntity<List<ProductoDto>> getProducts() {
        List<ProductoDto> productos = productoServicePort.getProducts();
        if (productos != null) {
            return ResponseEntity.ok(productos);
        }
        return ResponseEntity.badRequest().build();
    }
    @GetMapping("/getALl")
    public Flux<ProductoDto> getAllProducts() {
       List<ProductoDto> productos = productoServicePort.getProducts();
        return  productos == null ? Flux.empty() : Flux.fromIterable(productos);
    }
    @GetMapping("/getPage")
    public ResponseEntity<?> getProductsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Map<String, ? extends Serializable>> productoPage =   productoServicePort.findAllPage(page, size).stream().map(
                    productoDto -> Map.of(
                            "id", productoDto.getId(),
                            "name", productoDto.getNombre(),
                            "description", productoDto.getDescripcion(),
                            "price", productoDto.getPrecio(),
                            "imagen", productoDto.getImagen()
                    )
            ).collect(Collectors.toList());

            List<Map<String, ? extends Serializable>> response = productoPage;
            return new ResponseEntity< >(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductoDtoResponse> getProductByID(@PathVariable Integer id) {
        ProductoDtoResponse productoDtoResponse = new ProductoDtoResponse();
        productoDtoResponse = productoServicePort.getProductoDtoById(id);
        if (productoDtoResponse != null)
            return ResponseEntity.ok(productoDtoResponse);
        else
            return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductByID(
            @PathVariable Integer id) {

        productoServicePort.deleteProductoDto(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/kafka/create")
    public ResponseEntity<?> create(@Valid @RequestBody ProductoDto dto) throws ExecutionException, InterruptedException, TimeoutException {
        Reply<?> reply = productCommandServicePort.sendCreateAndAwait(dto, Duration.ofSeconds(5));
        return getResponseEntity(reply);
    }
    @PostMapping("/webflux/create")
    public Mono<ProductoDtoResponse> update(@Valid @RequestBody ProductoDto dto) throws ExecutionException, InterruptedException, TimeoutException {
        ProductoDtoResponse productoDtoResponse = new ProductoDtoResponse();
        productoDtoResponse = productoServicePort.addProductoDto(dto);
        return Mono.justOrEmpty(productoDtoResponse);
    }

    private static @NonNull ResponseEntity<?> getResponseEntity(Reply<?> reply) {
        if("SUCCESS".equalsIgnoreCase(reply.status())){
            return ResponseEntity.ok(reply.data());
        }
        return ResponseEntity.badRequest().body(Map.of("error", reply.message()));
    }

}
