package org.yearup.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart
{
    private int userId;  // userId field

    private Map<Integer, ShoppingCartItem> items = new HashMap<>();// only one items field

    @JsonProperty("itemsList")
    public List<ShoppingCartItem> getItemsList()
    {
        return new ArrayList<>(items.values());
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, ShoppingCartItem> getItems()
    {
        return items;
    }

    public void setItems(Map<Integer, ShoppingCartItem> items)
    {
        this.items = items;
    }

    public boolean contains(int productId)
    {
        return items.containsKey(productId);
    }

    public void add(ShoppingCartItem item)
    {
        items.put(item.getProductId(), item);
    }

    public ShoppingCartItem get(int productId)
    {
        return items.get(productId);
    }

    public BigDecimal getTotal()
    {
        BigDecimal total = items.values()
                .stream()
                .map(i -> i.getLineTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total;
    }
}
