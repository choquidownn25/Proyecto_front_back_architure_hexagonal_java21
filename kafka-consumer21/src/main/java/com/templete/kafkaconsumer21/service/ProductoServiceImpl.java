package com.templete.kafkaconsumer21.service;


import com.templete.kafkaconsumer21.models.dto.ProductoDTO;
import com.templete.kafkaconsumer21.models.entity.ProductoEntity;
import com.templete.kafkaconsumer21.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements IProduceService{

    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoDTO> findAll() {
            List<ProductoEntity> productoEntities = (List<ProductoEntity>) productoRepository.findAll();
            return productoEntities.stream()
                    .map(productoEntity -> new ProductoDTO(
                            productoEntity.getId(),
                            productoEntity.getCantidad(),
                            productoEntity.getDescripcion(),
                            productoEntity.getImagen(),
                            productoEntity.getNombre(),
                            productoEntity.getPrecio()
                    ))
                    .toList();
    }

    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        ProductoEntity productoEntitysave = ProductoEntity.builder()
                .cantidad(productoDTO.getCantidad())
                .descripcion(productoDTO.getDescripcion())
                .imagen(productoDTO.getImagen())
                .nombre(productoDTO.getNombre())
                .precio(productoDTO.getPrecio())
                .build();
        System.out.println("Se ha producido un producto : " + productoEntitysave);
        ProductoEntity productoEntity = productoRepository.save(productoEntitysave);
        return new ProductoDTO(productoEntity.getId(), productoEntity.getCantidad(), productoEntity.getDescripcion(), productoEntity.getImagen(), productoEntity.getNombre(), productoEntity.getPrecio());
    }

    @Override
    public ProductoDTO findById(Long id) {
        return productoRepository.findById(id)
                .map(productoEntity ->
                        new ProductoDTO(productoEntity.getId(),
                                productoEntity.getCantidad(),
                                productoEntity.getDescripcion(),
                                productoEntity.getImagen(),
                                productoEntity.getNombre(),
                                productoEntity.getPrecio()
                        )
                )
                .orElseThrow(() -> new RuntimeException("No existe el producto con el id: " + id));
    }


    @Override
    public ProductoDTO update(Long id, ProductoDTO dto) {
        ProductoEntity entity = productoRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
        entity.setImagen(dto.getImagen());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCantidad(dto.getCantidad());
        ProductoEntity productoEntity = productoRepository.save(entity);
        return new ProductoDTO(productoEntity.getId(), productoEntity.getCantidad(), productoEntity.getDescripcion(), productoEntity.getImagen(), productoEntity.getNombre(), productoEntity.getPrecio());
    }

    @Override
    public boolean delete(Long id) {
        boolean result = productoRepository.existsById(id);
        if (result) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
