package com.product.webservice.orders;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

public class Orders {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private Integer product_id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String email;




}
