package Service.cart;

import Entity.Cart;

import java.util.List;

public interface CartService {
    Cart addToCart(Long userId, Long productId, int quantity);
    List<Cart> getAllCartItemsForUser(Long userId);
    void removeCartItem(Long userId, Long productId);
    void clearCartForUser(Long userId);
}
