package Service.user;

import Entity.User;

import java.util.Map;

public interface UserService {
    Map<String, String> signup(User user);
    Map<String, String> login(String username, String password);

    User findByUsername(String username);
}
