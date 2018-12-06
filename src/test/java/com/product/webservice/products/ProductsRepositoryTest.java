package com.product.webservice.products;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductsRepositoryTest {
    @Autowired
    ProductsRepository productsRepository;

    @After
    public void cleanup(){
        productsRepository.deleteAll();
    }

    @Test
    public void getAllProducts(){
        //given
        productsRepository.save(Products.builder()
            .name("shirt")
            .comment("this is shirt")
            .price(30.5)
            .build());

        //when
        List<Products> productsList = productsRepository.findAll();

        //then
        Products products = productsList.get(0);
        assertEquals(products.getName(),"shirt");;
        assertEquals(products.getComment(), "this is shirt");

    }

    @Test
    public void BaseTimeEntity(){
        //given
        LocalDateTime now = LocalDateTime.now();
        productsRepository.save(Products.builder()
            .name("jacket")
            .comment("This is jacket")
            .price(63.2)
            .build());

        //when
        List<Products> productsList = productsRepository.findAll();

        //then
        Products products = productsList.get(0);
        assertTrue(products.getCreatedDate().isAfter(now));
        assertTrue(products.getModifiedDate().isAfter(now));


    }
}
