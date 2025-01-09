package controllers;

import Entity.Cart;
import Service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestParam int userId, @RequestParam int productId, @RequestParam int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @GetMapping("/{userId}")
    public List<Cart> getCartForUser(@PathVariable int userId) {
        return cartService.getAllCartItemsForUser(userId);
    }

    @DeleteMapping("/remove")
    public String removeCartItem(@RequestParam int userId, @RequestParam int productId) {
        cartService.removeCartItem(userId, productId);
        return "Removed product " + productId + " from cart for user " + userId;
    }

    @DeleteMapping("/clear/{userId}")
    public String clearCart(@PathVariable int userId) {
        cartService.clearCartForUser(userId);
        return "Cleared cart for user " + userId;
    }
}