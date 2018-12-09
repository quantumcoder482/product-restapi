package com.product.webservice.product;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity

public class Products extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(columnDefinition = "NUMBER")
    private Double price;

    @Builder
    public Products(Long id, String name, String comment, Double price){
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.price = price;
    }

}
