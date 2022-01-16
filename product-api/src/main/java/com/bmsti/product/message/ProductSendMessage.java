package com.bmsti.product.message;

import com.bmsti.product.dto.ProductDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductSendMessage {

    @Value("${product.rabbitmq.exchange}")
    String exchange;

    @Value("${product.rabbitmq.routingKey}")
    String routingKey;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProductSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ProductDTO productDTO) {
        rabbitTemplate.convertAndSend(exchange, routingKey, productDTO);
    }
}
