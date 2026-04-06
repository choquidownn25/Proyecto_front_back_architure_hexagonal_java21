package org.example.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.exemple.data.ProductoDto;
import org.exemple.data.response.ProductoDtoResponse;
import org.exemple.ports.api.ProductCommandServicePort;
import org.exemple.ports.api.ProductoServicePort;
import org.exemple.utils.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.jspecify.annotations.NonNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
    private static @NonNull ResponseEntity<?> getResponseEntity(Reply<?> reply) {
        if("SUCCESS".equalsIgnoreCase(reply.status())){
            return ResponseEntity.ok(reply.data());
        }
        return ResponseEntity.badRequest().body(Map.of("error", reply.message()));
    }

}
