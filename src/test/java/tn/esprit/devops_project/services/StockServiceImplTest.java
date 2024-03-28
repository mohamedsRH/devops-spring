package tn.esprit.devops_project.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class StockServiceImplTest {

    @Autowired
    StockRepository stockRepository;

    StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        stockService = new StockServiceImpl(stockRepository);
    }

    @AfterEach
    void tearDown() {
        stockRepository.deleteAll();
    }

    @Test
    void addStock() {
        Stock stock = new Stock();
        stock.setTitle("Test Stock");

        Stock savedStock = stockService.addStock(stock);
        Stock retrievedStock = stockRepository.findById(savedStock.getIdStock()).orElse(null);

        assertEquals(stock, retrievedStock);
    }

    @Test
    void retrieveStock() {
        Stock stock = new Stock();
        stock.setTitle("Test Stock");
        Stock savedStock = stockRepository.save(stock);

        Stock retrievedStock = stockService.retrieveStock(savedStock.getIdStock());

        assertEquals(stock, retrievedStock);
    }

    @Test
    void retrieveAllStock() {
        Stock stock1 = new Stock();
        stock1.setTitle("Stock 1");
        Stock stock2 = new Stock();
        stock2.setTitle("Stock 2");
        stockRepository.save(stock1);
        stockRepository.save(stock2);

        List<Stock> stocks = stockService.retrieveAllStock();

        assertEquals(2, stocks.size());
    }
}
