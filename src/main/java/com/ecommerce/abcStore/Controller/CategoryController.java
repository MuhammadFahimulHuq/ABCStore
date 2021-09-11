package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.Category;
import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Service.CartService;
import com.ecommerce.abcStore.Service.CategoryService;
import com.ecommerce.abcStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;

@Controller

public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public ModelAndView getCategory(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
        modelAndView.addObject("categoryList",categoryService.getAllCategory());
        modelAndView.setViewName("category");
        return modelAndView;
    }
    @RequestMapping(value = "/category",method = RequestMethod.POST)
    public ModelAndView setCategory(@Valid Category category, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Category existCategory = categoryService.getCategoryByCategoryName(category.getCategoryName());
        if (existCategory != null) {
            bindingResult
                    .rejectValue("categoryName", "error.category",
                            "Already Category Exist");
        } else if (bindingResult.hasErrors()) {
            modelAndView.setViewName("category");
        } else {

            categoryService.createCategory(category);
            modelAndView.addObject("successMessage","New Category Created");
           modelAndView.addObject("category",new Category());
            modelAndView.setViewName("redirect:/category");

        }
        return modelAndView;
    }


    @RequestMapping(value ="/update/{id}",method = RequestMethod.GET)
    public ModelAndView updateCategory(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Category category=categoryService.getCategoryById(id);
        modelAndView.addObject("category", category);
        modelAndView.setViewName("category_update");
        return modelAndView;

}
    @RequestMapping(value ="/update/{id}",method = RequestMethod.POST)
    public ModelAndView updateCategory(@PathVariable("id") Integer id, @Valid Category category) {
        ModelAndView modelAndView = new ModelAndView();
        categoryService.updateCategory(id, category);
        modelAndView.setViewName("redirect:/category");
        return modelAndView;
    }
    @RequestMapping(value ="/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        categoryService.deleteCategory(id);
        modelAndView.addObject("successDeleteMessage","Category Successfully Deleted");
        modelAndView.setViewName("redirect:/category");
        return modelAndView;
    }
}
