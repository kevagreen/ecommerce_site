package com.ecommerce.site.service;

import com.ecommerce.site.model.Product;
import com.ecommerce.site.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

   public List<Product> findAll(){
       return productRepository.findAll();
   }

   public Product findById(int id){
       return  productRepository.findById(id);
   }

   public List<String> findDistinctBrands(){
       return productRepository.findDistinctBrands();
   }

    public List<String> findDistinctCategory(){
        return productRepository.findDistinctCategory();
    }
    public void deleteById(int id){
       productRepository.deleteById(id);
    }

    public void save(Product product){
       productRepository.save(product);
    }

    public List<Product> findByBrandAndOrCategory(String brand, String category) {

        if (category == null && brand == null) {
            return productRepository.findAll();
        } else if (category == null) {
            return productRepository.findByBrand(brand);
        } else if (brand == null) {
            return productRepository.findByCategory(category);
        } else {
            return productRepository.findByBrandAndCategory(brand, category);
        }
    }
}
