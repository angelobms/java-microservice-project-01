package com.bmsti.payment.dto;

import com.bmsti.payment.entity.Product;
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

@JsonPropertyOrder({"id", "stock"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductDTO extends RepresentationModel<ProductDTO> implements Serializable {

    @JsonProperty(" id")
    private Long id;

    @JsonProperty("stock")
    private Integer stock;

    public static ProductDTO create(Product product) {
        return new ModelMapper().map(product, ProductDTO.class);
    }

}
