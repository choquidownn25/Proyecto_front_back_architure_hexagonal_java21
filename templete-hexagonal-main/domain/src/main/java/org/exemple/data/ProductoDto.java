package org.exemple.data;

import lombok.*;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductoDto implements Serializable {

    private Integer id;

    private Integer cantidad;

    private String descripcion;

    private String imagen;

    private String nombre;

    private Double precio;



}