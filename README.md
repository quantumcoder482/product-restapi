# product-restapi

Product Rest API is demonstrating hot to implement CRUD operation with `Products` and `Orders` entity.

## What is inside

This project is base on the [Spring Boot](http://projects.spring.io/spring-boot) project and use these packages  :

- Gradle
- Lombok
- Spring Core
- Mokito Testing(for Unit test)

## Installation 
The project is created with Maven and Lombok, So you just need to import it to your IDE and build the project to reslove the dependencies


## Usage
Run the project through the IDE and head our to [http://localhost:8080](http://localhost:8080)

or 

run this command in the command line:
````
mvn spring-boot:run

## REST API End Points

GET request to /products  return all products
GET request to /products/{id}  returns a product by id
POST request to /products  save one product  
    Body Data is JSON type
    product: 
    {
        "name": "jacket",                              // String
        "comment": "This is shirt jacket",             // String   
        "price":65.3                                   // Double
    }
PUT request  to /products/{id}  update a product by product id
    Body Data type is like as POST 
DELETE request to /products/{id}  delete the product by id

GET request to /orders/{periodstartdate}/{periodenddate}   return orders by period
POST request to /orders        save one order                      
    Body Data is JSON type
    {
        "email":"asd@example.com",                     // String
        "productList":"1,3,5",                         // String     this field is product Ids
        "orderDate":"2018-10-20"                       // String
    }
PUT request to /orders/{id}   update a order by order id
PUT request to /recalcorder/{id}   update amount of a order
DELETE request to /orders/{id} deletes the order by id






