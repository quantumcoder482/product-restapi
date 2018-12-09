package com.product.webservice.dto;

import com.product.webservice.product.Products;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductsSaveRequestDto {
    private Long id;
    private String name;
    private String comment;
    private Double price;

    public Products toEntity(){
        return Products.builder()
                .id(id)
                .name(name)
                .comment(comment)
                .price(price)
                .build();
    }
}
