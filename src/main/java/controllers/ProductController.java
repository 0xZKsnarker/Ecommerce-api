package controllers;

import Entity.Product;
import Service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService theProductService) {
        productService = theProductService;
    }

    @GetMapping("/products")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return productService.findById(productId);
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product theProduct) {
        // set ID to 0 (or null) if you want a new product always
        theProduct.setId(null);
        return productService.save(theProduct);
    }

    @PutMapping("/products")
    public Product updateProduct(@RequestBody Product theProduct) {
        return productService.update(theProduct);
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
        return "Deleted product with id " + productId;
    }
}
