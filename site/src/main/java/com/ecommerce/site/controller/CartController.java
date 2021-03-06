package com.ecommerce.site.controller;


import com.ecommerce.site.model.Product;
import com.ecommerce.site.model.User;
import com.ecommerce.site.service.ProductService;
import com.ecommerce.site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
public class CartController {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @ModelAttribute("loggedInUser")
    public User loggedInUser(){
        return userService.getLoggedInUser();
    }

    @ModelAttribute("cart")
    public Map<Product, Integer> cart(){
        User user = loggedInUser();
        if(user == null) return null;
        System.out.println("Getting cart");
        return user.getCart();
    }


    @ModelAttribute("list")
    public List<Double> list(){
        return new ArrayList<>();
    }

    @GetMapping(value = "/cart")
    public String showCart(){
        return "cart";
    }


    @PostMapping(value = "/cart")
    public String addToCart(@RequestParam int id) {
        Product p = productService.findById(id);
        setQuantity(p, cart().getOrDefault(p, 0) + 1);

        return "cart";
    }


    @PatchMapping(value = "/cart")
    public String updateQuantities(@RequestParam int[] id, @RequestParam int[] quantity){
        for(int i=0; i < id.length; i++){
            Product product = productService.findById(id[i]);
            setQuantity(product, quantity[i]);

        }
        return "cart";
    }

    @PostMapping(value = "/cart/{id}")
    public String removeFromCart(@PathVariable int id){
        Product p = productService.findById(id);
        setQuantity(p, 0);
        return "cart";
    }

    private void setQuantity(Product product, int quantity){
        if(quantity > 0){
            cart().put(product, quantity);
        }else{
            cart().remove(product);
            userService.updateCart(cart());
        }
    }
}
