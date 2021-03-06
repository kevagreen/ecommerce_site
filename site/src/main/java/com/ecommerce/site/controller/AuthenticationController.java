package com.ecommerce.site.controller;

import com.ecommerce.site.model.User;
import com.ecommerce.site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/signin")
    public String login(){
        return "signin";
    }


    @PostMapping(value = "/signin")
    public String signup(@Valid User user, @RequestParam String submit, BindingResult bindingResult, HttpServletRequest request ) throws ServletException {

        String password = user.getPassword();
        if(submit.equals("up")){
            if(userService.findByUsername(user.getUsername()) == null){
               userService.saveNew(user);
            }else{
                bindingResult.rejectValue("username", "error.user", "username is already taken");
                return "signin";
            }
        }
        request.login(user.getUsername(), password);
        return "redirect:/";
    }

}
