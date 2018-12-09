package com.product.webservice.web;

import com.product.webservice.dto.OrdersSaveRequestDto;
import com.product.webservice.orders.Orders;
import com.product.webservice.orders.OrdersRepository;
import com.product.webservice.product.Products;
import com.product.webservice.product.ProductsRepository;
import com.product.webservice.dto.ProductsSaveRequestDto;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor

public class WebRestController {

    private ProductsRepository productsRepository;
    private OrdersRepository ordersRepository;


    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping("/products")
    public ResponseEntity<Object> saveProducts(@RequestBody ProductsSaveRequestDto dto){
        Products savedProduct = productsRepository.save(dto.toEntity());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/products")
    public List<Products> retrieveAllProduct(){
        return productsRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Products retrieveProduct(@PathVariable Long id) throws ProductNotFoundException {
        Optional<Products> product = productsRepository.findById(Long.valueOf(id));
        if(!product.isPresent())
            throw new ProductNotFoundException("id-" + id);
        return product.get();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody Products product, @PathVariable Long id){
        Optional<Products> productsOptional = productsRepository.findById(id);
        if(!productsOptional.isPresent())
            return ResponseEntity.notFound().build();
        product.setId(Long.valueOf(id));
        productsRepository.save(product);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/orders")
    public ResponseEntity<Object> saveOrders(@RequestBody Orders orders){
        String listArray[] = orders.getProductList().split(",");
        Double amountTemp = 0.0;
        for (int i = 0; i < listArray.length; i ++){
            amountTemp += productsRepository.findById(Long.parseLong(listArray[i])).get().getPrice();
        }
        Orders tempOrder = new Orders(orders.getId(),orders.getEmail(),orders.getProductList(),amountTemp,orders.getOrderDate());
        Orders savedOrder = ordersRepository.save(tempOrder);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrder.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Object> updateOrder(@RequestBody Orders order, @PathVariable Long id){
        Optional<Orders> ordersOptional = ordersRepository.findById(id);

        Orders tempOrder = new Orders(ordersOptional.get().getId(), ordersOptional.get().getEmail(),ordersOptional.get().getProductList(),ordersOptional.get().getAmount(),ordersOptional.get().getOrderDate());
        if(order.getOrderDate() != null){
            tempOrder.setOrderDate(order.getOrderDate());
        }
        if(order.getEmail() != null){
            tempOrder.setEmail(order.getEmail());
        }
        if(order.getAmount() != null){
            tempOrder.setAmount(order.getAmount());
        }
        if(order.getProductList() != null){
            tempOrder.setProductList(order.getProductList());
        }

        tempOrder.setId(Long.valueOf(id));
        ordersRepository.save(tempOrder);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders/{startperiod}/{endperiod}")
    public List<Orders> getOrdersByPeriod(@PathVariable String startperiod, @PathVariable String endperiod){
        List<Orders> ordersList = ordersRepository.findAll();
        List<Orders> tempOrdersList = new ArrayList<>();
        for(int i = 0; i < ordersList.size(); i ++ ){
            if(ordersList.get(i).getOrderDate().compareTo(startperiod) >= 1  && ordersList.get(i).getOrderDate().compareTo(endperiod) <= -1  ){
                tempOrdersList.add(ordersList.get(i));
            }

        }
        return tempOrdersList;
    }

    @PutMapping("/recalcorders/{id}")
    public ResponseEntity<Object> recalculateOrder(@PathVariable Long id){
        Optional<Orders> orders= ordersRepository.findById(Long.valueOf(id));
        String listArray[] = orders.get().getProductList().split(",");
        Double amountTemp = 0.0;
        for (int i = 0; i < listArray.length; i ++){
            amountTemp += productsRepository.findById(Long.parseLong(listArray[i])).get().getPrice();

        }
        Orders tempOrder = new Orders(orders.get().getId(),orders.get().getEmail(),orders.get().getProductList(),amountTemp,orders.get().getOrderDate());
        tempOrder.setId(id);
        Orders savedOrder = ordersRepository.save(tempOrder);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOrder.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

//    @DeleteMapping
//    public void deleteProduct(@PathVariable Long id){
//        productsRepository.deleteById(Long.valueOf(id));
//    }
//
//    @DeleteMapping
//    public void deleteOrder(@PathVariable Long id){
//        ordersRepository.deleteById(Long.valueOf(id));
//    }

}
