package controllers;

import Entity.Order;
import Entity.OrderStatus;
import Entity.User;
import Service.order.OrderService;
import Service.user.UserService; // or wherever your user methods are
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * Checkout endpoint:
     * Typically, you'd get the user from the JWT.
     * You can skip the userId param if you want to always derive from auth.
     */
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(Authentication authentication) {
        // Get username from the JWT context
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        // Create the order from the user's cart
        Order order = orderService.createOrder(user.getId());

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId,
                                          Authentication authentication) {
        // Typically, you’d verify the order belongs to the user (or the user is admin).
        Order order = orderService.findOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Order>> getMyOrders(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Order> orders = orderService.findOrdersByUserId(user.getId());
        return ResponseEntity.ok(orders);
    }

    /**
     * Only Admins should typically update order status
     * (e.g., from PENDING to SHIPPED, etc.).
     * You can add @PreAuthorize("hasRole('ADMIN')") here.
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @RequestParam String status) {
        OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
