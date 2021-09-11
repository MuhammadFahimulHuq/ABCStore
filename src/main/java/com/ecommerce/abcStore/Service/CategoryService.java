package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.Category;
import com.ecommerce.abcStore.Repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    public Category getCategoryById(int id){
        return categoryRepository.findById(id).orElse(null);
    }

    public Category updateCategory(int id,Category category){
        Category updateCategory = getCategoryById(id);
        updateCategory.setCategoryName(category.getCategoryName());
        return createCategory(updateCategory);
    }
    public String deleteCategory(int id){
        Category findCategory = categoryRepository.findById(id).orElse(null);
        categoryRepository.delete(findCategory);
        return "category id: "+ findCategory.getId()+ " deleted" ;
    }

    public Category getCategoryByCategoryName(String categoryName){
        return categoryRepository.findByCategoryName(categoryName) ;
    }
}
