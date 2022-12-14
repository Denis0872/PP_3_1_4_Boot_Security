package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.getUser(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found (byId)"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public User update(User user, Long id) {
        User user1 = userRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("User not found (update)"));
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Optional<User> oldUser = userRepository.findById(user.getId());
        if(oldUser.isEmpty() || !oldUser.get().getPassword().equals(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }


    @Override
    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("User not found (delete)"));
        userRepository.delete(user);
    }
}
