package com.bmsti.payment.services;

import com.bmsti.payment.dto.SaleDTO;
import com.bmsti.payment.entity.ProductSale;
import com.bmsti.payment.entity.Sale;
import com.bmsti.payment.exceptions.ResourceNotFoundException;
import com.bmsti.payment.repository.ProductRepository;
import com.bmsti.payment.repository.ProductSaleRepository;
import com.bmsti.payment.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final static String SALE_NOT_FOUND = "No sale found for the ID %d";

    private final SaleRepository saleRepository;
    private final ProductSaleRepository productSaleRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository, ProductSaleRepository productSaleRepository) {
        this.saleRepository = saleRepository;
        this.productSaleRepository = productSaleRepository;
    }

    public SaleDTO create(SaleDTO saleDTO) {
        Sale sale = saleRepository.save(Sale.create(saleDTO));

        List<ProductSale> products = new ArrayList<>();
        saleDTO.getProducts().forEach(p -> {
            ProductSale productSale = ProductSale.create(p);
            productSale.setSale(sale);
            products.add(productSaleRepository.save(productSale));
        });
        sale.setProducts(products);

        return SaleDTO.create(sale);
    }

    public Page<SaleDTO> findAll(Pageable pageable) {
        var page = saleRepository.findAll(pageable);
        return page.map(this::covertToSaleDTO);
    }

    private SaleDTO covertToSaleDTO(Sale sale) {
        return SaleDTO.create(sale);
    }

    public SaleDTO findById(Long id) {
        var sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(SALE_NOT_FOUND, id)));
        return SaleDTO.create(sale);
    }
}
