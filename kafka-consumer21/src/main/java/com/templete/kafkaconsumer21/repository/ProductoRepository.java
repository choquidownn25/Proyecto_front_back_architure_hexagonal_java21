package com.templete.kafkaconsumer21.repository;


import com.templete.kafkaconsumer21.models.entity.ProductoEntity;
import org.springframework.data.repository.CrudRepository;


public interface ProductoRepository extends CrudRepository<ProductoEntity, Long> {
}
