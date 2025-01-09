package Service.order;

import Entity.Order;
import Entity.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId);
    Order findOrderById(Long orderId);
    List<Order> findOrdersByUserId(Long userId);
    Order updateOrderStatus(Long orderId, OrderStatus status);
}
