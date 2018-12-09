package com.product.webservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.webservice.orders.Orders;
import com.product.webservice.orders.OrdersRepository;
import com.product.webservice.product.Products;
import com.product.webservice.product.ProductsRepository;
import com.product.webservice.web.WebRestController;
import com.sun.xml.internal.ws.api.model.MEP;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;
import java.nio.charset.Charset;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;



@RunWith(SpringRunner.class)
@SpringBootTest

public class WebRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static MediaType HTML_CONTENT_TYPE = new MediaType(MediaType.TEXT_HTML.getType(), MediaType.TEXT_HTML.getSubtype(), Charset.forName("utf8"));
    private static MediaType JSON_CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    private WebRestController webRestController;

    @After
    public void cleanupProducts(){
        productsRepository.deleteAll();
    }
    @After
    public void cleanupOrders(){ ordersRepository.deleteAll(); }

    @Before
    public void setup() throws Exception{
        mockMvc = standaloneSetup(webRestController).build();
        objectMapper = new ObjectMapper();
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Test
    public void saveProductsTest() throws Exception{
        //given
        Products sampleProduct = Products.builder()
                .name("shirt")
                .comment("this is shirt")
                .price(30.5)
                .build();

        String sampleProductJson = this.jsonStringFromObject(sampleProduct);


        //when
        mockMvc.perform(post("/products")
                .contentType(JSON_CONTENT_TYPE)
                .content(sampleProductJson))
        .andDo(print())
        .andExpect(status().isCreated());

    }

    @Test
    public void retrieveAllProductTest() throws Exception{
        //given
        productsRepository.save(Products.builder()
                .name("shirt")
                .comment("this is shirt")
                .price(30.5)
                .build());
        productsRepository.save(Products.builder()
                .name("jacket")
                .comment("this is jacket")
                .price(60.6)
                .build());

        //when
        List<Products> productsList = productsRepository.findAll();

        String jsonString = this.jsonStringFromObject(productsList);

        //then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE));

    }

    @Test
    public void retrieveProduct() throws Exception{
        //given
        Products sampleProduct = Products.builder()
                .name("shirt")
                .comment("this is shirt")
                .price(30.5)
                .build();

        //when

        String jsonString = this.jsonStringFromObject(sampleProduct);

        //then
        mockMvc.perform(get("/products")
                .param("id","1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(JSON_CONTENT_TYPE));

    }

    @Test
    public void updateProductTest() throws Exception{
        //given
        productsRepository.save(Products.builder()
                .id(1L)
                .name("shirt")
                .comment("this is shirt")
                .price(30.5)
                .build());

        Products sampleProduct = Products.builder()
                .id(1L)
                .name("shirt")
                .comment("this is shirt")
                .price(30.5)
                .build();
        //when

        String jsonString = this.jsonStringFromObject(sampleProduct);

        //then
        mockMvc.perform(put("/products/{id}", 1)
                .contentType(JSON_CONTENT_TYPE)
                .content(jsonString))
        .andDo(print());
//        .andExpect(status().isCreated());
    }

    @Test
    public void saveOrdersTest() throws Exception{
        //given
        productsRepository.save(Products.builder()
                .id(1L)
                .name("shirt")
                .comment("this is shirt")
                .price(30.5)
                .build());

        Orders sampleOrder = Orders.builder()
                .email("asd@example.com")
                .productList("1")
                .orderDate("2018-10-20")
                .build();

        //when
        String jsonString = this.jsonStringFromObject(sampleOrder);

        //then
        mockMvc.perform(post("/orders")
                .contentType(JSON_CONTENT_TYPE)
                .content(jsonString))
        .andDo(print())
        .andExpect(status().isCreated());
    }

    @Test
    public void updateOrdersTest() throws Exception{
        //given

        ordersRepository.save(Orders.builder()
                .id(1L)
                .email("as@example.com")
                .productList("1")
                .orderDate("2018-10-25")
                .build());

        Orders sampleOrder = Orders.builder()
                .id(1L)
                .email("asd@example.com")
                .productList("2")
                .orderDate("2018-10-20")
                .build();

        //when
        String jsonString = this.jsonStringFromObject(sampleOrder);

        System.out.println(jsonString);
        //then
        mockMvc.perform(put("/orders/{id},",1)
                .contentType(JSON_CONTENT_TYPE)
                .content(jsonString))
        .andDo(print());
//        .andExpect(status().isOk());
    }

    @Test
    public void getOrdersTest() throws Exception{
        //given

        ordersRepository.save(Orders.builder()
                .email("as1@example.com")
                .productList("1")
                .orderDate("2018-10-25")
                .build());

        ordersRepository.save(Orders.builder()
                .email("as2@example.com")
                .productList("2")
                .orderDate("2018-10-25")
                .build());

        //when

        //then
        mockMvc.perform(get("/orders/{startperiod}/{endperiod}","2018-01-31","2018-12-30"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void recalcOrdersTest() throws Exception{
        //given
        ordersRepository.save(Orders.builder()
                .id(1L)
                .email("asd@example.com")
                .productList("2")
                .orderDate("2018-10-25")
                .build());
        productsRepository.save(Products.builder()
                .id(2L)
                .name("shirt")
                .comment("this is shirt")
                .price(30.5)
                .build());


        //when


        //then
        mockMvc.perform(put("/recalcorders/{id}", 1))
                .andDo(print())
                .andExpect(status().isCreated());
    }


}
