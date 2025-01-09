package Service.product;

import Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
   private ProductRepository productRepository;


   public ProductServiceImpl(ProductRepository theProductRepository){
        productRepository = theProductRepository;
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
    public Product findById(long theId) {
        return productRepository.findById(theId)
                .orElseThrow(() -> new RuntimeException("Did not find product id - " + theId));
    }


    @Override
    public void deleteById(long theId) {
        productRepository.deleteById(theId);
    }
}
