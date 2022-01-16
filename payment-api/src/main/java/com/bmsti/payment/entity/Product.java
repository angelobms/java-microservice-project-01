package com.bmsti.payment.entity;

import com.bmsti.payment.dto.ProductDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "stock", nullable = false, length = 10)
    private Integer stock;

    public static Product create(ProductDTO productDTO) {
        return new ModelMapper().map(productDTO, Product.class);
    }
}
