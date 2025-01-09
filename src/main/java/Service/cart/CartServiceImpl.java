package Service.cart;

import Entity.Cart;
import Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CartRepository;
import repository.ProductRepository;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart addToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        return cartRepository.findByUserIdAndProductId(userId, productId)
                .map(existingCart -> {
                    existingCart.setQuantity(existingCart.getQuantity() + quantity);
                    return cartRepository.save(existingCart);
                })
                .orElseGet(() -> {
                    Cart newCartItem = new Cart();
                    newCartItem.setUserId(userId);
                    newCartItem.setProductId(productId);
                    newCartItem.setQuantity(quantity);
                    return cartRepository.save(newCartItem);
                });
    }

    @Override
    public List<Cart> getAllCartItemsForUser(Long userId) {
        return cartRepository.findAllByUserId(userId);
    }

    @Override
    public void removeCartItem(Long userId, Long productId) {
        cartRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(cartRepository::delete);
    }

    @Override
    public void clearCartForUser(Long userId) {
        cartRepository.deleteAllByUserId(userId);
    }
}
