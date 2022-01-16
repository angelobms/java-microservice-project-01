package com.bmsti.payment.entity;

import com.bmsti.payment.dto.ProductSaleDTO;
import com.bmsti.payment.dto.SaleDTO;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sale")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sale", cascade = {CascadeType.REFRESH})
    private List<ProductSale> products;

    @Column(name = "total", nullable = false, length = 10)
    private Double total;

    public static Sale create(SaleDTO saleDTO) {
        return new ModelMapper().map(saleDTO, Sale.class);
    }
}
