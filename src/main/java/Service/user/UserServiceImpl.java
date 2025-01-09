package Service.user;

import Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import util.JwtUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Map<String, String> signup(User user) {
        // Basic signup logic.
        // You may already have a similar method, just ensure user is being saved with hashed password.

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Signup successful");
        return response;
    }

    @Override
    public Map<String, String> login(String username, String password) {
        // Basic login logic.
        // Adapt to your existing flow if needed.

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Generate JWT
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole().name());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        return response;
    }

    @Override
    public User findByUsername(String username) {
        // Return the user if found, or null (or throw exception) if not
        return userRepository.findByUsername(username).orElse(null);
    }
}
