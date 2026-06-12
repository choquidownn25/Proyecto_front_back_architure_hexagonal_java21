package org.example.mappers;

import org.example.entity.Producto;
import org.exemple.data.ProductoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;
import org.springframework.data.domain.Page;
@Mapper
public interface ProductoMapper {
    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoDto productoDtoToProducto(Producto product);

    Producto productoToProductoDto(ProductoDto productoDto);

    //listado
    List<ProductoDto> ProductoDtoListToProductoList(List<Producto> productoList);
    // Método principal para mapear automáticamente el Page
    default Page<ProductoDto> toPageDTO(Page<Producto> paginaEntidad) {
        return paginaEntidad.map(this::productoDtoToProducto);
    }
}

