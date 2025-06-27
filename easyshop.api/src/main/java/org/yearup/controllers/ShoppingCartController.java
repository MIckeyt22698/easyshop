package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()") // restrict access to logged-in users only
public class ShoppingCartController
{
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // GET /cart
    @GetMapping("")
    public ShoppingCart getCart(Principal principal)
    {
        try
        {
            String username = principal.getName();
            int userId = userDao.getByUserName(username).getId();
            return shoppingCartDao.getCartByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // POST /cart/products/{productId} - adds product and returns updated cart
    @PostMapping("/products/{productId}")
    public ShoppingCart addProduct(@PathVariable int productId, Principal principal)
    {
        try
        {
            String username = principal.getName();
            int userId = userDao.getByUserName(username).getId();

            shoppingCartDao.addItem(userId, productId, 1);

            // Return updated cart so frontend can refresh immediately
            return shoppingCartDao.getCartByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // PUT /cart/products/{productId} - update quantity
    @PutMapping("/products/{productId}")
    public void updateQuantity(@PathVariable int productId, @RequestBody Map<String, Integer> body, Principal principal)
    {
        try
        {
            int quantity = body.get("quantity");
            String username = principal.getName();
            int userId = userDao.getByUserName(username).getId();
            shoppingCartDao.updateQuantity(userId, productId, quantity);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // DELETE /cart - clear cart and return empty cart
    @DeleteMapping("")
    public ShoppingCart clearCart(Principal principal)
    {
        try
        {
            String username = principal.getName();
            int userId = userDao.getByUserName(username).getId();

            shoppingCartDao.clearCart(userId);

            // Return empty cart after clearing
            return shoppingCartDao.getCartByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
