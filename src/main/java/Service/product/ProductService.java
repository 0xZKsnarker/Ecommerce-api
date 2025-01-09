package Service.product;

import Entity.Product;

import java.util.List;

public interface ProductService  {


    Product save(Product theProduct);
    Product update(Product theProduct);
    List<Product> findAll();
    Product findById(long theId);
    void deleteById(long theId);

}