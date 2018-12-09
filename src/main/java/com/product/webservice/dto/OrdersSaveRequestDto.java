package com.product.webservice.dto;

import com.product.webservice.orders.Orders;

import com.product.webservice.product.ProductsRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class OrdersSaveRequestDto {

    private Long id;
    private String email;
    private String productList;

    private ProductsRepository productsRepository;

    public Orders toEntity(){
        String listArray[] = productList.split(",");
        Double amountTemp = 0.0;

        for (int i = 0; i < listArray.length; i ++){

            amountTemp += productsRepository.findById(Long.parseLong(listArray[i])).get().getPrice();
        }
        return Orders.builder()
                .id(id)
                .email(email)
                .productList(productList)
                .amount(amountTemp)
                .build();

    }
}
