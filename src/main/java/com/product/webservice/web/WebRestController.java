package com.product.webservice.web;

import com.product.webservice.products.ProductsRepository;
import com.product.webservice.dto.products.ProductsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor

public class WebRestController {

    private ProductsRepository productsRepository;


    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/products")
    public void saveProducts(@RequestBody ProductsSaveRequestDto dto){
        productsRepository.save(dto.toEntity());
    }

}
