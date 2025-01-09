
package Service.order;

import Entity.*;
import Service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order createOrder(Long userId) {
        // 1) Fetch User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 2) Get cart items for this user
        List<Cart> cartItems = cartService.getAllCartItemsForUser(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot create order.");
        }

        // 3) Calculate total price
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // 4) Create Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(totalPrice);
        // Set paymentId if needed, e.g., order.setPaymentId(paymentId)
        Order savedOrder = orderRepository.save(order);

        // 5) For each cart item, create an OrderItem
        for (Cart cart : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cart.getProduct());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setPriceAtPurchase(cart.getProduct().getPrice());
            orderItemRepository.save(orderItem);

            // 6) Decrement product stock
            Product product = cart.getProduct();
            product.setQuantity(product.getQuantity() - cart.getQuantity());
            productRepository.save(product);
        }

        // 7) Clear the cart
        cartService.clearCartForUser(userId);

        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = findOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
