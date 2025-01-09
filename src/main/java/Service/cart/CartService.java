package Service.cart;

import Entity.Cart;
import java.util.List;

public interface CartService {
    Cart addToCart(int userId, int productId, int quantity); // Add or update a cart item
    List<Cart> getAllCartItemsForUser(int userId); // Get all cart items for a user
    void removeCartItem(int userId, int productId); // Remove specific cart item
    void clearCartForUser(int userId); // Clear all cart items for a user
}
