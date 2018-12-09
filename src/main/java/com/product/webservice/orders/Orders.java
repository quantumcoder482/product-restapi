package com.product.webservice.orders;

import com.product.webservice.product.Products;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter

@Entity

public class Orders{
    @Id
    @GeneratedValue
    private Long id;
    private String productList;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String email;

//    @Column(columnDefinition = "NUMBER", nullable = false)
    private Double amount;

    private String orderDate;



    @Builder
    public Orders(Long id, String email,String productList,Double amount, String orderDate){
        this.id = id;
        this.email = email;
        this.productList = productList;
        this.amount = amount;
        this.orderDate = orderDate;
    }



}
