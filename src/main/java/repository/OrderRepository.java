package repository;

import Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Example of a custom finder to get all orders for a specific user
    List<Order> findByUserId(Long userId);
}
