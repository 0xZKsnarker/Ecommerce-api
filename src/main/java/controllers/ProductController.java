package controllers;

import Entity.Product;
import Service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController (ProductService theProductService)
    {
        productService = theProductService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("products/{productId}")
    public Product getProduct(@PathVariable int productId){
        Product theProduct = productService.findById(productId);

        if (theProduct == null) {
            throw new RuntimeException("Employee id not found - " + productId);
        }
        return theProduct;
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product theProduct){
        if (theProduct == null){
            throw new RuntimeException("The product is missing details");
        }
        theProduct.setId(0);
        Product dbProduct = productService.save(theProduct);
        return dbProduct;
    }

    @PutMapping("products")
    public Product updateProduct(@RequestBody Product theProduct){
        if (theProduct == null){
            throw new RuntimeException("The product is missing details");
        }
        Product dbProduct = productService.update(theProduct);
        return dbProduct;
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable int productId){
        Product tempProduct = productService.findById(productId);

        if (tempProduct == null){
            throw new RuntimeException("Product id not found " + productId);
        }
        productService.deleteById(productId);
    return "Deleted product with id " + productId;
    }

}
