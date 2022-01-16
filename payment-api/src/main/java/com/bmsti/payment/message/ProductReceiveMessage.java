package com.bmsti.payment.message;

import com.bmsti.payment.dto.ProductDTO;
import com.bmsti.payment.entity.Product;
import com.bmsti.payment.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductReceiveMessage {

    private final ProductRepository productRepository;

    @Autowired
    public ProductReceiveMessage(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RabbitListener(queues = {"${product.rabbitmq.queue}"})
    public void receive(@Payload ProductDTO productDTO) {
        productRepository.save(Product.create(productDTO));
    }
}
