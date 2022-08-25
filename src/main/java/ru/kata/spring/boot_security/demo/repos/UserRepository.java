package ru.kata.spring.boot_security.demo.repos;

import org.springframework.data.repository.CrudRepository;
import ru.kata.spring.boot_security.demo.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
