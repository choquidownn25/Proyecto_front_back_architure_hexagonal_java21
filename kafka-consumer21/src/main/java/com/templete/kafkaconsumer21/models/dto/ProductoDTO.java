package com.templete.kafkaconsumer21.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    private Integer cantidad;
    private String descripcion;
    private String imagen;
    private String nombre;
    private Double precio;
}
