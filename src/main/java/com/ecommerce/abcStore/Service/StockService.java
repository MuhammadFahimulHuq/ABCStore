package com.ecommerce.abcStore.Service;

import com.ecommerce.abcStore.Model.Product;
import com.ecommerce.abcStore.Model.Stock;
import com.ecommerce.abcStore.Repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class StockService {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductService productService;

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }
    public List<Stock> getAllStock(){
        return stockRepository.findAll();
    }
    public Stock getStockById(long id){
        return stockRepository.findById(id).orElse(null);
    }
    public Stock updateStock(long id,Stock stock){
        Stock updateStock = getStockById(id);
        updateStock.setTotalUnit(stock.getTotalUnit());
        updateStock.setTotalUnitPrice(stock.getTotalUnitPrice());
        updateStock.setShipmentDate(stock.getShipmentDate());
        return createStock(updateStock);
    }
    public String deleteStock(long id){
        Stock findStock = getStockById(id);
        findStock.setProduct(null);
        stockRepository.deleteById(findStock.getId());
        return "Stock id:"+findStock.getId()+" deleted";
    }
    public Stock getStockByProductId(long id){return stockRepository.findStockByProductId(id);}
}
