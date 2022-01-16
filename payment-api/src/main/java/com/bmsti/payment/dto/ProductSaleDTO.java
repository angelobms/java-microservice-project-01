package com.bmsti.payment.dto;

import com.bmsti.payment.entity.ProductSale;
import com.bmsti.payment.entity.Sale;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@JsonPropertyOrder({"id", "productId", "amount"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductSaleDTO extends RepresentationModel<ProductSaleDTO> implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("amount")
    private Integer amount;

    public static ProductSaleDTO create(ProductSale productSale) {
        return new ModelMapper().map(productSale, ProductSaleDTO.class);
    }
}
