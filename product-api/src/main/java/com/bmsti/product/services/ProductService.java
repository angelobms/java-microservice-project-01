package com.bmsti.product.services;

import com.bmsti.product.dto.ProductDTO;
import com.bmsti.product.entity.Product;
import com.bmsti.product.exceptions.ResourceNotFoundException;
import com.bmsti.product.message.ProductSendMessage;
import com.bmsti.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final static String PRODUCT_NOT_FOUND = "No product found for the ID %d";

    private final ProductRepository productRepository;
    private final ProductSendMessage productSendMessage;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductSendMessage productSendMessage) {
        this.productRepository = productRepository;
        this.productSendMessage = productSendMessage;
    }

    public ProductDTO create(ProductDTO productDTO) {
        ProductDTO productCreated = ProductDTO.create(productRepository.save(Product.create(productDTO)));
        productSendMessage.sendMessage(productCreated);
        return productCreated;
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        var page = productRepository.findAll(pageable);
        return page.map(this::covertToProductDTO);
    }

    private ProductDTO covertToProductDTO(Product product) {
        return ProductDTO.create(product);
    }

    public ProductDTO findById(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, id)));
        return ProductDTO.create(product);
    }

    public ProductDTO update(ProductDTO productDTO) {
        final Optional<Product> optionalProduct = productRepository.findById(productDTO.getId());
        if(!optionalProduct.isPresent()) {
            throw new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, productDTO.getId()));
        }
        return ProductDTO.create(productRepository.save(Product.create(productDTO)));
    }

    public void delete(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, id)));
        productRepository.delete(product);
    }
}
