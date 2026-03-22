package com.example.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.reactive.function.client.WebClient;

import com.example.orderservice.dto.OrderResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Mono;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final WebClient webClient;

    public OrderController(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackResponse")
    public Mono<OrderResponse> getOrder(@PathVariable("id") Long id) {

        return webClient.get()
            .uri("http://PRODUCT-SERVICE/products/" + id)
            .retrieve()
            .bodyToMono(Object.class)
            .map(product -> new OrderResponse(id, product))
            .onErrorResume(ex -> fallbackResponse(id, ex));
                
    }

    // MUST match signature EXACTLY
    public Mono<OrderResponse> fallbackResponse(Long id, Throwable ex) {
        return Mono.just(new OrderResponse(
                id,
                "Fallback: Product Service is DOWN"
        ));
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public String placeOrder(@RequestBody String order) {
        kafkaTemplate.send("order-topic", order);
        return "Order Sent to Kafka";
}
}