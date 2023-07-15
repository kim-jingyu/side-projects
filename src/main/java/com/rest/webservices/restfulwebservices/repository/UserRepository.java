package com.rest.webservices.restfulwebservices.repository;

import com.rest.webservices.restfulwebservices.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository {

    private static List<User> users = new ArrayList<>();

    private static Long id = 0L;

    static {
        users.add(new User(++id, "Kim", LocalDateTime.now().minusYears(30)));
        users.add(new User(++id, "Lee", LocalDateTime.now().minusYears(25)));
        users.add(new User(++id, "Park", LocalDateTime.now().minusYears(20)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User save(User user) {
        user.setId(++id);
        users.add(user);
        return user;
    }

    public void deleteById(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
