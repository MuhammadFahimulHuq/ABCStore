package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.Category;
import com.ecommerce.abcStore.Model.ProductPrice;
import com.ecommerce.abcStore.Repository.CategoryRepository;
import com.ecommerce.abcStore.Repository.ProductPriceRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProductPriceService {
    @Autowired
    private ProductPriceRepository productPriceRepository;

    public ProductPrice createProductPrice(ProductPrice productPrice){
        return productPriceRepository.save(productPrice);
    }

    public List<ProductPrice> getAlProductPrice(){
        return productPriceRepository.findAll();
    }
    public ProductPrice getProductPriceById(long id){
        return productPriceRepository.findById(id).orElse(null);
    }

    public ProductPrice updateCategory(int id,ProductPrice productPrice){
        ProductPrice updateCategory = getProductPriceById(id);

        return createProductPrice(updateCategory);
    }
    public String deleteCategory(int id){
        ProductPrice findProductPrice = getProductPriceById(id);
        productPriceRepository.delete( findProductPrice);
        return "category id: "+  findProductPrice.getId()+ " deleted" ;
    }



}
