import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.controllers.ShoppingCartController;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShoppingCartControllerTest {

    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;
    private ShoppingCartController controller;
    private Principal principal;

    @BeforeEach
    public void setup() {
        shoppingCartDao = mock(ShoppingCartDao.class);
        userDao = mock(UserDao.class);
        productDao = mock(ProductDao.class);
        controller = new ShoppingCartController(shoppingCartDao, userDao, productDao);
        principal = mock(Principal.class);
    }

    @Test
    public void getCart_returnsCart() {
        // Arrange
        when(principal.getName()).thenReturn("admin");
        User user = new User();
        user.setId(1);
        when(userDao.getByUserName("admin")).thenReturn(user);
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(1);
        when(shoppingCartDao.getCartByUserId(1)).thenReturn(cart);

        // Act
        ShoppingCart result = controller.getCart(principal);

        // Assert
        assertEquals(1, result.getUserId());
    }

    @Test
    public void addProduct_addsItem() {
        // Arrange
        when(principal.getName()).thenReturn("admin");
        User user = new User();
        user.setId(1);
        when(userDao.getByUserName("admin")).thenReturn(user);

        // Act
        controller.addProduct(7, principal);

        // Assert
        verify(shoppingCartDao).addItem(1, 7, 1);
    }

    @Test
    public void updateQuantity_updatesQuantity() {
        // Arrange
        when(principal.getName()).thenReturn("admin");
        User user = new User();
        user.setId(1);
        when(userDao.getByUserName("admin")).thenReturn(user);
        Map<String, Integer> body = Map.of("quantity", 5);

        // Act
        controller.updateQuantity(99, body, principal);

        // Assert
        verify(shoppingCartDao).updateQuantity(1, 99, 5);
    }

    @Test
    public void clearCart_clearsCart() {
        // Arrange
        when(principal.getName()).thenReturn("admin");
        User user = new User();
        user.setId(1);
        when(userDao.getByUserName("admin")).thenReturn(user);

        // Act
        controller.clearCart(principal);

        // Assert
        verify(shoppingCartDao).clearCart(1);
    }
}