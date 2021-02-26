package com.ecommerce.site.service;

import com.ecommerce.site.model.Product;
import com.ecommerce.site.model.User;
import com.ecommerce.site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveNew(User user){

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        userRepository.save(user);
    }

    public User getLoggedInUser(){
        return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public void saveExisting(User user){
        userRepository.save(user);
    }

    public void updateCart(Map<Product, Integer> cart){
        User user = getLoggedInUser();
        user.setCart(cart);
        saveExisting(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = findByUsername(username);
        if(user == null)throw new UsernameNotFoundException("username or password not found");
        return user;
    }
}
