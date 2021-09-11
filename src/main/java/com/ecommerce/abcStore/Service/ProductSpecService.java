package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.ProductSpec;
import com.ecommerce.abcStore.Repository.ProductRepository;
import com.ecommerce.abcStore.Repository.ProductSpecRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecService {
    @Autowired
    private ProductSpecRepository productSpecRepository;


    public ProductSpec createProductSpec(ProductSpec productSpec){

        return productSpecRepository.save(productSpec);
    }
    public List<ProductSpec> getAllProduct(){
        return productSpecRepository.findAll();
    }
    public ProductSpec getProductSpecById(long id){return productSpecRepository.findById(id).orElse(null); }
    public ProductSpec updateCategory(long id, ProductSpec productSpec){
        ProductSpec updateProductSpec = getProductSpecById(id);
        updateProductSpec.setBrand(productSpec.getBrand());
        updateProductSpec.setDescription(productSpec.getDescription());
        updateProductSpec.setGender(productSpec.getGender());
        updateProductSpec.setWeight(productSpec.getWeight());
        updateProductSpec.setWeight(productSpec.getWeight());
        return createProductSpec(updateProductSpec);
    }
    public String deleteProductSpec(long id){
        ProductSpec findProductSpec = getProductSpecById(id);
        productSpecRepository.delete(findProductSpec);
        return  "product id: "+findProductSpec.getId()+" deleted";
    }

}
