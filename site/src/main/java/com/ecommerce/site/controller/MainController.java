package com.ecommerce.site.controller;

import com.ecommerce.site.model.Product;
import com.ecommerce.site.service.ProductService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Controller
@ControllerAdvice
public class MainController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/")
    public String main(){
        return "main";
    }

    @ModelAttribute("products")
    public List<Product> products(){
        return productService.findAll();
    }


    @ModelAttribute("categories")
    public List<String> categories(){
        return productService.findDistinctCategory();
    }


    @ModelAttribute("brands")
    public List<String> brands(){
        return productService.findDistinctBrands();
    }

    @GetMapping(value = "/filter")
    public String filter(@RequestParam(required = false) String category, @RequestParam(required = false) String brand, Model model){
        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
        model.addAttribute("products", filtered);
        return "main";
    }
    @GetMapping(value = "/about")
    public String about(){
        return "about";
    }
}
