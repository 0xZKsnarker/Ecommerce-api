package Service.product;

import Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository theProductRepository){
        this.productRepository = theProductRepository;
    }

    @Override
    public Product save(Product theProduct) {
        return productRepository.save(theProduct);
    }

    @Override
    public Product update(Product theProduct) {
        return productRepository.save(theProduct);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long theId) {
        return productRepository.findById(theId)
                .orElseThrow(() -> new RuntimeException("Did not find product id - " + theId));
    }

    @Override
    public void deleteById(Long theId) {
        productRepository.deleteById(theId);
    }
}
