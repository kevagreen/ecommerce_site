package com.ecommerce.site.controller;

import com.ecommerce.site.model.Product;
import com.ecommerce.site.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "/product/{id}")
    public String show(@PathVariable int id, Model model){
        Product product = productService.findById(id);
        model.addAttribute(product);
        return "product";
    }

    @ModelAttribute("products")
    public List<Product> products(){
        return productService.findAll();
    }
    @RequestMapping(value = "/product", method = {RequestMethod.POST, RequestMethod.PUT})
    public String createOrUpdate(@Valid Product product){
        productService.save(product);
        return "redirect:/product/" + product.getId();
    }

//    @GetMapping(value = "/cartItem")
//    public String addItem(Model model){
//        Product productInCart = new Product();
//        model.addAttribute("product", productInCart);
////        return "cartItem";
//    }

}
