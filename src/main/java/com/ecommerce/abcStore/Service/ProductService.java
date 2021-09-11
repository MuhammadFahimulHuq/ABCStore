package com.ecommerce.abcStore.Service;


import com.ecommerce.abcStore.Model.*;
import com.ecommerce.abcStore.Repository.CategoryRepository;
import com.ecommerce.abcStore.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(Product product){

        return productRepository.save(product);
    }
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
    public Product getProductById(long id){return productRepository.findById(id).orElse(null); }
    public Product updateProduct(long id, Product product){
        Product updateProduct = getProductById(id);
        updateProduct.setProductName(product.getProductName());
        updateProduct.setProductPrice(product.getProductPrice());
        updateProduct.setProductSpec(product.getProductSpec());
       return createProduct(updateProduct);
    }
    public String deleteProduct(long id){
        Product findProduct = getProductById(id);
        productRepository.delete(findProduct);
        return  "product id: "+findProduct.getId()+" deleted";
    }
    public Product getProductByProductName(String productName){
        return productRepository.getProductByProductName(productName);
    }
    public void saveImage(MultipartFile imageFile) throws Exception {
        Path currentPath = Paths.get(".");
        byte[] bytes =  imageFile.getBytes();
        Path path = Paths.get(currentPath.toAbsolutePath()+"/src/main/resources/static/photos/"+imageFile.getOriginalFilename());
        Files.write(path,bytes);
    }


}
