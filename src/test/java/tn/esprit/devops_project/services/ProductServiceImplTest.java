package tn.esprit.devops_project.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addProduct() {
        Long idStock = 1L;
        Stock stock = new Stock();
        Product product = new Product();
        when(stockRepository.findById(idStock)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product, idStock);

        assertEquals(product, result);
        assertEquals(stock, result.getStock());
    }

    @Test
    void retrieveProduct() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.retrieveProduct(id);

        assertEquals(product, result);
    }

    @Test
    void retrieveProduct_NotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> productService.retrieveProduct(id));
    }

    @Test
    void retreiveAllProduct() {
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.retreiveAllProduct();

        assertEquals(products, result);
    }

    @Test
    void retrieveProductByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> products = new ArrayList<>();
        when(productRepository.findByCategory(category)).thenReturn(products);

        List<Product> result = productService.retrieveProductByCategory(category);

        assertEquals(products, result);
    }

    @Test
    void deleteProduct() {
        Long id = 1L;
        doNothing().when(productRepository).deleteById(id);

        productService.deleteProduct(id);

        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void retreiveProductStock() {
        Long id = 1L;
        List<Product> products = new ArrayList<>();
        when(productRepository.findByStockIdStock(id)).thenReturn(products);

        List<Product> result = productService.retreiveProductStock(id);

        assertEquals(products, result);
    }
}
