package com.product.webservice.product;


import com.product.webservice.orders.Orders;
import com.product.webservice.orders.OrdersRepository;
import org.hibernate.criterion.Order;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest

public class OrdersRepositoryTest {
    @Autowired
    OrdersRepository ordersRepository;

    @After
    public void cleanup(){
        ordersRepository.deleteAll();
    };

    @Test
    public void getAllOrders(){
        //given
        ordersRepository.save(Orders.builder()
                .productList("1,2,3")
                .email("wujin@example.com")
                .amount(123.2)
                .orderDate("2018-12-10")
                .build());

        //when
        List<Orders> OrdersList = ordersRepository.findAll();

        //then
        Orders Orders = OrdersList.get(0);
        assertEquals(Orders.getProductList(),"1,2,3");
        assertEquals(Orders.getEmail(),"wujin@example.com");
        assertEquals(Orders.getOrderDate(),"2018-12-10");
        assertEquals(Orders.getAmount(),Double.valueOf(123.2));

    }



}
