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
import java.util.Date;
import java.util.List;

@JsonPropertyOrder({"id", "date", "products", "total"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaleDTO extends RepresentationModel<SaleDTO> implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("products")
    private List<ProductSaleDTO> products;

    @JsonProperty("total")
    private Double total;

    public static SaleDTO create(Sale sale) {
        return new ModelMapper().map(sale, SaleDTO.class);
    }
}
