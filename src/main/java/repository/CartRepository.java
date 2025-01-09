package repository;

import Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // Find all cart items for a specific user
    List<Cart> findAllByUserId(int userId);

    // Find a specific cart item by user and product
    Optional<Cart> findByUserIdAndProductId(int userId, int productId);

    // Remove all cart items for a specific user
    void deleteAllByUserId(int userId);
}
