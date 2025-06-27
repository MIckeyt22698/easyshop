import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.controllers.ProductsController;
import org.yearup.data.ProductDao;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductsControllerTest {

    private ProductDao productDao;
    private ProductsController controller;

    @BeforeEach
    public void setUp() {
        productDao = mock(ProductDao.class);
        controller = new ProductsController(productDao);
    }

    @Test
    public void search_withFilters_shouldReturnMatchingProducts() {
        Product p = new Product(1, "Shirt", new BigDecimal("29.99"), 2, "T-shirt", "Red", 10, false, "img.jpg");

        when(productDao.search(2, new BigDecimal("20"), new BigDecimal("30"), "Red"))
                .thenReturn(List.of(p));

        List<Product> result = controller.search(2, new BigDecimal("20"), new BigDecimal("30"), "Red");

        assertEquals(1, result.size());
        assertEquals("Shirt", result.get(0).getName());
    }

    @Test
    public void search_withNoFilters_shouldReturnAll() {
        when(productDao.search(null, null, null, null))
                .thenReturn(Collections.emptyList());

        List<Product> result = controller.search(null, null, null, null);
        assertNotNull(result);
    }

    @Test
    public void getById_whenFound_shouldReturnProduct() {
        Product product = new Product(10, "Bag", new BigDecimal("79.99"), 3, "Travel bag", "Black", 5, true, "bag.jpg");

        when(productDao.getById(10)).thenReturn(product);

        Product result = controller.getById(10);

        assertEquals("Bag", result.getName());
    }

    @Test
    public void getById_whenNotFound_shouldThrowNotFound() {
        when(productDao.getById(99)).thenReturn(null);

        Exception ex = assertThrows(RuntimeException.class, () -> controller.getById(99));
        assertTrue(ex.getMessage().contains("Oops"));
    }

    @Test
    public void addProduct_shouldReturnCreatedProduct() {
        Product p = new Product(0, "Watch", new BigDecimal("149.99"), 1, "Smart Watch", "Silver", 8, false, "watch.jpg");

        when(productDao.create(p)).thenReturn(p);

        Product result = controller.addProduct(p);

        assertEquals("Watch", result.getName());
    }

    @Test
    public void updateProduct_shouldCallDaoUpdate() {
        Product p = new Product(5, "Wallet", new BigDecimal("29.99"), 2, "Leather wallet", "Brown", 12, false, "wallet.jpg");

        controller.updateProduct(5, p);

        verify(productDao, times(1)).update(5, p);
    }

    @Test
    public void deleteProduct_whenFound_shouldCallDelete() {
        Product existing = new Product(3, "Belt", new BigDecimal("19.99"), 2, "Men's belt", "Black", 7, false, "belt.jpg");

        when(productDao.getById(3)).thenReturn(existing);

        controller.deleteProduct(3);

        verify(productDao).delete(3);
    }

    @Test
    public void deleteProduct_whenNotFound_shouldThrowNotFound() {
        when(productDao.getById(404)).thenReturn(null);

        Exception ex = assertThrows(RuntimeException.class, () -> controller.deleteProduct(404));
        assertTrue(ex.getMessage().contains("Oops"));
    }
}
