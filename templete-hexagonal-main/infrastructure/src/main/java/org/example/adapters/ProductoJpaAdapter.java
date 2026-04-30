package org.example.adapters;

import org.example.entity.Producto;
import org.example.mappers.ProductoMapper;
import org.example.repository.ProductoRepository;
import org.exemple.data.ProductoDto;
import org.exemple.ports.spi.ProductoPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoJpaAdapter implements ProductoPersistencePort {

    public static final String PRODUCT = "PRODUCT";
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public ProductoDto addProductoDto(ProductoDto productoDto) {
        // convert DTO to Entity
        ModelMapper modelMapper = null;
        Producto postRequest = ProductoMapper.INSTANCE.productoToProductoDto(productoDto);
        Producto productoSave = productoRepository.save(postRequest);
        ProductoDto retornProductoDto = ProductoMapper.INSTANCE.productoDtoToProducto(productoSave);
        return retornProductoDto;
    }

    @Override
    public ProductoDto updateProductoDto(ProductoDto productoDto) {
        ModelMapper modelMapper = null;
        Producto postRequest = ProductoMapper.INSTANCE.productoToProductoDto(productoDto);
        Producto productoSave = productoRepository.save(postRequest);
        ProductoDto retornProductoDto = ProductoMapper.INSTANCE.productoDtoToProducto(productoSave);
        return retornProductoDto;
    }
    //@Cacheable(value = "productos", key = "#id")
    @Override
    public void deleteProductoDto(Integer id) {
        productoRepository.deleteById(id);
    }


    @Override
    @Cacheable(value="ProductDto")
    public List<ProductoDto> getProducts() {
        //Lista todos los registros
        List<Producto> listProducts = productoRepository.findAll();
        return ProductoMapper.INSTANCE.ProductoDtoListToProductoList(listProducts);

    }

    @Override
    @Cacheable(value="ProductDto", key="#id")
    public ProductoDto getProductoDtoById(Integer id) {
        //Encuentra un registro
        Optional<Producto> productoId = productoRepository.findById(id);
        if (productoId.isPresent()) {
            return ProductoMapper.INSTANCE.productoDtoToProducto(productoId.get());
        }
        return null;
    }
}
