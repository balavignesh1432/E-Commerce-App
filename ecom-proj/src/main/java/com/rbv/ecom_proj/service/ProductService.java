package com.rbv.ecom_proj.service;

import com.rbv.ecom_proj.model.Product;
import com.rbv.ecom_proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImage(imageFile.getBytes());
        return repo.save(prod);
    }

    public void deleteProductById(int id) {
        repo.deleteById(id);
    }

    public Product updateProductById(int id, Product prod, MultipartFile imageFile) throws IOException{
        if(repo.findById(id).orElse(null) == null){
            return null;
        }
        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImage(imageFile.getBytes());
        return repo.save(prod);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
