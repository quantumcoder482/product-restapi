package com.product.webservice.products;

import lombok.*;
import org.dom4j.swing.XMLTableColumnDefinition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

public class Products extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    private Double price;

    @Builder
    public Products(String name, String comment, Double price){
        this.name = name;
        this.comment = comment;
        this.price = price;
    }


}
