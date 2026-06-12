package org.exemple.ports.api;

import org.exemple.data.ProductoDto;
import org.exemple.data.response.ProductoDtoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductoServicePort {
    ProductoDtoResponse addProductoDto(ProductoDto productoDto);
    ProductoDtoResponse updateProductoDto(ProductoDto productoDto);
    void deleteProductoDto(Integer id);
    List<ProductoDto> getProducts();
    ProductoDtoResponse getProductoDtoById(Integer id);
    Page<ProductoDto> findAllPage(int page, int size);
}
