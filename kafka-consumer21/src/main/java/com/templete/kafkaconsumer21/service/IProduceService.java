package com.templete.kafkaconsumer21.service;



import com.templete.kafkaconsumer21.models.dto.ProductoDTO;

import java.util.List;

public interface IProduceService {
    List<ProductoDTO> findAll();
    ProductoDTO save(ProductoDTO productoDTO);
    ProductoDTO findById(Long id);
    ProductoDTO update(Long id, ProductoDTO dto);
    boolean delete(Long id);
}
