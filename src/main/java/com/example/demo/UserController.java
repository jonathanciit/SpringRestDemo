package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }
    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = users.stream().filter(u -> u.getId().equals(id)).findFirst();
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Create new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUserFromHeader(@RequestHeader String name, @RequestHeader(required = false) String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setId(counter.incrementAndGet());
        users.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Update existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User
            userDetails) {
        User user = null;
        for(User u: users){
            if(u.getId().equals(id)){
                user = u;
                break;
            }
        }
        ///Optional<User> userOptional = users.stream().filter(u -> u.getId().equals(id)).findFirst();
        //if (userOptional.isPresent()) {
        if(user!=null){
            //User user = userOptional.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = users.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (userOptional.isPresent()) {
            users.remove(userOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sample")
    public String showSample() {
        return "sample";
    }
}

