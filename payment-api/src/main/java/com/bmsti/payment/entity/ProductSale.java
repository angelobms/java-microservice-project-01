package com.bmsti.payment.entity;

import com.bmsti.payment.dto.ProductDTO;
import com.bmsti.payment.dto.ProductSaleDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_sale")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductSale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, length = 10)
    private Long productId;

    @Column(name = "amount", nullable = false, length = 10)
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    public static ProductSale create(ProductSaleDTO productSaleDTO) {
        return new ModelMapper().map(productSaleDTO, ProductSale.class);
    }
}
