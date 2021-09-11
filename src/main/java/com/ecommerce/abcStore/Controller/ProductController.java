package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.Category;
import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.Review;
import com.ecommerce.abcStore.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductPriceService productPriceService;
    @Autowired
    private ProductSpecService productSpecService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private StockService stockService;
    @RequestMapping(value = "/product",method = RequestMethod.GET)
    public ModelAndView getProduct(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
        modelAndView.addObject("category",categoryService.getAllCategory());
        modelAndView.addObject("products",productService.getAllProduct());
        modelAndView.setViewName("product");
        return modelAndView;
    }
    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public ModelAndView getProductCard(HttpSession session, @PathVariable("id") Long id, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryList",categoryService.getAllCategory());
        modelAndView.addObject("product",productService.getProductById(id));
        modelAndView.addObject("category",categoryService.getAllCategory());
        modelAndView.addObject("InStock",stockService.getStockByProductId(productService.getProductById(id).getId()));
        modelAndView.addObject("review",new Review());
        modelAndView.addObject("reviews",reviewService.getReviewByProductId(productService.getProductById(id).getId()));
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            modelAndView.addObject("cartSize",'0');
        }
        else{
            modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
            modelAndView.addObject("cartSize",cartService.getCartByUserId(userService.findUserByEmail(authentication.getName()).getId()).size());
        }
        modelAndView.setViewName("browseProduct");
        return modelAndView;
    }

    @RequestMapping(value = "/product",method = RequestMethod.POST)
    public ModelAndView createProduct(@Valid Product product, BindingResult bindingResult,@RequestParam("imageFront") MultipartFile imageFront,@RequestParam("imageBack") MultipartFile imageBack) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Product existProduct = productService.getProductByProductName(product.getProductName());


        if(existProduct !=null){
            bindingResult
                    .rejectValue("productName","error.product",
                            "Already Product Exist");

        }
        else if(bindingResult.hasErrors()){
            modelAndView.setViewName("product");
        }
        else {
            byte[] bytes =  imageFront.getBytes();
            product.setProductImageFront(bytes);
            bytes = imageBack.getBytes();
            product.setProductImageBack(bytes);
            float hundred = 100;
            float getDiscountPercentage = hundred-((product.getProductPrice().getDiscountPrice()/(product.getProductPrice().getPrice())*hundred));
            float roundDiscount= BigDecimal.valueOf(getDiscountPercentage)
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                    .floatValue();
            product.getProductPrice().setDiscountPercentage(roundDiscount);
            Category category = categoryService.getCategoryById(product.getCategory().getId());
            product.setCategory(category);

            productPriceService.createProductPrice(product.getProductPrice());
            productSpecService.createProductSpec(product.getProductSpec());
            productService.createProduct(product);
            category.getProducts().add(product);
            categoryService.createCategory(category);
            modelAndView.addObject("successMessage","New Product Created");
            modelAndView.addObject("product",new Product());
            modelAndView.setViewName("redirect:/product");
        }
        try {
            productService.saveImage(imageFront);
            productService.saveImage(imageBack);

        } catch (Exception e) {
            e.printStackTrace();

        }


        return modelAndView;
    }
    @RequestMapping(value ="/productupdate/{id}",method = RequestMethod.GET)
    public ModelAndView updateProduct(@PathVariable("id") Long id,Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.getProductById(id);
        modelAndView.addObject("product",product);
        modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
        modelAndView.addObject("category",categoryService.getAllCategory());
        modelAndView.addObject("products",productService.getAllProduct());
        modelAndView.setViewName("product_update");
        return modelAndView;
    }


    @RequestMapping(value ="/productupdate/{id}",method = RequestMethod.POST)
    public ModelAndView updateProduct(@PathVariable("id") Integer id,@Valid Product product,BindingResult bindingResult,@RequestParam("imageFront") MultipartFile imageFront,@RequestParam("imageBack") MultipartFile imageBack) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Product existProduct = productService.getProductById(id);
         if(bindingResult.hasErrors()){
            modelAndView.setViewName("product_update");
        }
        else {
            byte[] bytes =  imageFront.getBytes();
            existProduct.setProductImageFront(bytes);
            bytes = imageBack.getBytes();
            existProduct.setProductImageBack(bytes);
            existProduct.setProductName(product.getProductName());
            existProduct.getProductPrice().setPrice(product.getProductPrice().getPrice());
            existProduct.getProductPrice().setDiscountPrice(product.getProductPrice().getDiscountPrice());
             float hundred = 100;
             float getDiscountPercentage = hundred-((product.getProductPrice().getDiscountPrice()/(product.getProductPrice().getPrice())*hundred));
             float roundDiscount= BigDecimal.valueOf(getDiscountPercentage)
                     .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                     .floatValue();
             existProduct.getProductPrice().setDiscountPercentage(roundDiscount);
             existProduct.setProductSpec(product.getProductSpec());
            productPriceService.createProductPrice(existProduct.getProductPrice());
            productSpecService.createProductSpec(existProduct.getProductSpec());
            Category existCategory= categoryService.getCategoryById(existProduct.getCategory().getId());
            existCategory.getProducts().remove(existProduct);
            Category category = categoryService.getCategoryById(product.getCategory().getId());
            existProduct.setCategory(category);
            productService.createProduct(existProduct);
            category.getProducts().add(existProduct);
            categoryService.createCategory(category);
            modelAndView.addObject("successMessage","Product Updated");
            modelAndView.setViewName("redirect:/product");
        }
        try {
            productService.saveImage(imageFront);
            productService.saveImage(imageBack);

        } catch (Exception e) {
            e.printStackTrace();

        }


        return modelAndView;
    }
    @RequestMapping(value ="/delete/product/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.getProductById(id);
        Category category= product.getCategory();
        category.setProducts(null);
        categoryService.createCategory(category);
        product.setCategory(null);
        productService.createProduct(product);
        productService.deleteProduct(product.getId());
        modelAndView.addObject("successDeleteMessage","Product Successfully Deleted");
        modelAndView.setViewName("redirect:/product");
        return modelAndView;
    }

//    @RequestMapping(value ="/update/product/{id}",method = RequestMethod.POST)
//    public ModelAndView updateCategory(@PathVariable("id") Integer id, @Valid Product product) {
//        ModelAndView modelAndView = new ModelAndView();
//        productService.updateProduct(id,product);
//        modelAndView.setViewName("redirect:/product");
//        return modelAndView;
//    }
}
