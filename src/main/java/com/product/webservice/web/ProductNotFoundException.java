package com.product.webservice.web;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(String exception) {
        super(exception);
    }
}
