package com.redhands.api_gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api")
public class ProxyController {

    private final WebClient.Builder webClientBuilder;

    @Value("${backend.inventory-service.url}")
    private String inventoryServiceUrl;

    @Value("${backend.order-service.url}")
    private String orderServiceUrl;

    public ProxyController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping("/parts")
    public String getAllParts() {
        return webClientBuilder.build()
                .get()
                .uri(inventoryServiceUrl + "/api/parts")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @PostMapping("/orders")
    public String createOrder(@RequestBody String orderRequest) {
        return webClientBuilder.build()
                .post()
                .uri(orderServiceUrl + "/api/orders")
                .header("Content-Type", "application/json")
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}