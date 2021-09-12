package com.ecommerce.abcStore.Controller;

import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.Stock;
import com.ecommerce.abcStore.Service.ProductService;
import com.ecommerce.abcStore.Service.StockService;
import com.ecommerce.abcStore.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/stock/{id}",method = RequestMethod.GET)
    public ModelAndView getProductStock(@PathVariable Long id, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("stock",new Stock());
        modelAndView.addObject("user",userService.findUserByEmail(authentication.getName()));
        modelAndView.addObject("stockList",stockService.getStockByProductId(productService.getProductById(id).getId()));
        modelAndView.addObject("product",productService.getProductById(id));
        modelAndView.setViewName("stock");
        return modelAndView;
    }
    @RequestMapping(value = "/admin/stock/{id}",method = RequestMethod.POST)
    public ModelAndView setProductStock(@PathVariable("id") Long id, @Valid Stock stock, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){
            modelAndView.setViewName("stock");
        }else {
            Product product=productService.getProductById(id);
            stock.setProduct(product);
            stock.setRemainingUnit(stock.getTotalUnit());
            stockService.createStock(stock);

           modelAndView.setViewName("redirect:/stock/{id}");
        }
        return modelAndView;
    }
    @RequestMapping(value = "/admin/product/stock/update/{id}",method = RequestMethod.GET)
    public ModelAndView updateProductStock(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("stock",new Stock());
        modelAndView.addObject("stockList",stockService.getStockById(productService.getProductById(id).getId()));
        modelAndView.addObject("productName",productService.getProductById(id));
        modelAndView.setViewName("stock_update");
        return modelAndView;
    }
    @RequestMapping(value = "/admin/product/stock/update/{id}",method = RequestMethod.POST)
    public ModelAndView updateProductStock(@PathVariable("id") Long id,@Valid Stock stock,BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("stock_update");
        }
        else{
            Stock findStock = stockService.getStockById(id);
            stockService.updateStock(findStock.getId(),findStock);
            modelAndView.setViewName("redirect:/stock/{id}");
        }
        return modelAndView;
    }
    @RequestMapping(value = "/admin/product/stock/delete/{id}",method = RequestMethod.GET)
    public ModelAndView deleteProductStock(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();;
        Stock stock = stockService.getStockById(id);
        stockService.deleteStock(stock.getId());
        modelAndView.addObject("successDeleteMessage","Stock Successfully Deleted");
        modelAndView.setViewName("redirect:/stock/{id}");
        return modelAndView;
    }
}
