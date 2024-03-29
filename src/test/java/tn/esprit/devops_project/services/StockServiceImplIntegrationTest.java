package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StockServiceImplIntegrationTest {

    @Autowired
    private StockRepository stockRepository;

    private StockServiceImpl stockService;

    @BeforeEach
    public void setUp() {
        stockService = new StockServiceImpl(stockRepository);
    }

    @Test
    public void testAddStock() {
        // Given
        Stock stockToAdd = new Stock();
        stockToAdd.setTitle("Test Stock");

        // When
        Stock savedStock = stockService.addStock(stockToAdd);

        // Then
        assertThat(savedStock).isNotNull();
        assertThat(savedStock.getIdStock()).isNotNull();
        assertThat(savedStock.getTitle()).isEqualTo("Test Stock");

        // Verify that the stock is saved in the database
        List<Stock> allStocks = stockRepository.findAll();
        assertThat(allStocks).hasSize(1);
        assertThat(allStocks.get(0)).isEqualTo(savedStock);
    }

    @Test
    public void testRetrieveStockById() {
        // Given
        Stock existingStock = new Stock();
        existingStock.setTitle("Existing Stock");
        stockRepository.save(existingStock);

        // When
        Stock retrievedStock = stockService.retrieveStock(existingStock.getIdStock());

        // Then
        assertThat(retrievedStock).isNotNull();
        assertThat(retrievedStock.getTitle()).isEqualTo(existingStock.getTitle());
    }

    @Test
    public void testRetrieveAllStock() {
        // Given
        Stock stock1 = new Stock();
        stock1.setTitle("Stock 1");

        Stock stock2 = new Stock();
        stock2.setTitle("Stock 2");

        stockRepository.saveAll(List.of(stock1, stock2));

        // When
        List<Stock> allStocks = stockService.retrieveAllStock();

        // Then
        assertThat(allStocks).isNotNull();
        assertThat(allStocks).hasSize(2);
        assertThat(allStocks).contains(stock1, stock2);
    }

    // Add more tests for other methods as needed
}
