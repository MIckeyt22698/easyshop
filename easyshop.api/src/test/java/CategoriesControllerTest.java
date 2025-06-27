import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.controllers.CategoriesController;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoriesControllerTest {

    private CategoryDao categoryDao;
    private ProductDao productDao;
    private CategoriesController controller;

    @BeforeEach
    public void setUp() {
        categoryDao = mock(CategoryDao.class);
        productDao = mock(ProductDao.class);
        controller = new CategoriesController(categoryDao, productDao);
    }

    @Test
    public void getAll_shouldReturnAllCategories() {
        List<Category> mockCategories = List.of(
                new Category(1, "Clothing", "clothes"),
                new Category(2, "Electronics", "computers")
        );

        when(categoryDao.getAll()).thenReturn(mockCategories);

        List<Category> result = controller.getAll();

        assertEquals(2, result.size());
        assertEquals("Clothing", result.get(0).getName());
    }

    @Test
    public void getById_shouldReturnCorrectCategory() {
        Category mockCategory = new Category(3, "Toys", "playable");

        when(categoryDao.getById(3)).thenReturn(mockCategory);

        Category result = controller.getById(3);

        assertEquals("Toys", result.getName());
    }

    @Test
    public void getProductsById_shouldReturnProductsForCategory() {
        List<Product> mockProducts = List.of(
                new Product(1, "Toy Car", new BigDecimal("10.99"), 3, "Mini car", "Red", 5, false, "img.jpg")
        );

        when(productDao.listByCategoryId(3)).thenReturn(mockProducts);

        List<Product> result = controller.getProductsById(3);

        assertEquals(1, result.size());
        assertEquals("Toy Car", result.get(0).getName());
    }

    @Test
    public void addCategory_shouldReturnCreatedCategory() {
        Category input = new Category(0, "Furniture", "Couches");
        Category created = new Category(10, "Furniture", "chairs");

        when(categoryDao.create(input)).thenReturn(created);

        Category result = controller.addCategory(input);

        assertEquals("Furniture", result.getName());
        assertEquals(10, result.getCategoryId());
    }

    @Test
    public void updateCategory_shouldCallDaoUpdate() {
        Category updated = new Category(2, "Updated Name", "new name");

        controller.updateCategory(2, updated);

        verify(categoryDao, times(1)).update(2, updated);
    }

    @Test
    public void deleteCategory_shouldCallDaoDelete() {
        controller.deleteCategory(7);

        verify(categoryDao, times(1)).delete(7);
    }
}
