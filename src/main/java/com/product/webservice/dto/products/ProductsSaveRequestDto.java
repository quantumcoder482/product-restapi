package com.product.webservice.dto.products;

import com.product.webservice.products.Products;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ProductsSaveRequestDto {
    private String name;
    private String comment;
    private Double price;

    public Products toEntity(){
        return Products.builder()
                .name(name)
                .comment(comment)
                .price(price)
                .build();
    }
}
