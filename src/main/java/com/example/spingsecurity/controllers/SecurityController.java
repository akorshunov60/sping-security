package com.example.spingsecurity.controllers;

import com.example.spingsecurity.entities.Product;
import com.example.spingsecurity.entities.User;
import com.example.spingsecurity.services.ProductService;
import com.example.spingsecurity.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SecurityController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/")
    public String homePage() {
        return "Home page";
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @GetMapping("/users/edit/{userId}")
    public String editUser(@PathVariable Long userId) {
        return "Edit users form / форма редактирования данных о пользователе с id = " + userId;
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @GetMapping("/users/add")
    public String addUser() {
        return  "Add user form / форма добавления нового пользователя";
    }

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/products/edit")
    public String editProductList() {
        return "Edit product list / страница со списком товаров с возможностью редактировать и создавать новые товары";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id) {
        return "Edit product form / форма редактирования товара с id = " + id;
    }
}
//UPDATE `test`.`users` SET `name` = 'admin' WHERE (`id` = '2');

//    @Override
//    public void addUser(Long id, String username, String password, String email) {
//        User user = userRepository.findById(userId).orElseThrow();
//        this.addUser(id, username, password, email);
//    }