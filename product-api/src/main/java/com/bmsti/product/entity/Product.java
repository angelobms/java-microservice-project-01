package com.bmsti.product.entity;

import com.bmsti.product.dto.ProductDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "stock", nullable = false, length = 10)
    private Integer stock;

    @Column(name = "price", nullable = false, length = 10)
    private Double price;

    public static Product create(ProductDTO productDTO) {
        return new ModelMapper().map(productDTO, Product.class);
    }
}
