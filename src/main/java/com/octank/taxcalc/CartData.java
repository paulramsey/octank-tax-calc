package com.octank.taxcalc;

import java.util.HashMap;
import java.util.Map;

public class CartData {
    public String cartId;
    public String productId;
    public String quantity;
    public String subtotal;

    public CartData(String cartId, String productId, String quantity, String subtotal) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

	public Map<String, String> getCartData() {
        Map<String, String> returnObject = new HashMap<String, String>();
        returnObject.put("cartId", this.cartId);
        returnObject.put("productId", this.productId);
        returnObject.put("quantity", this.quantity);
        returnObject.put("subtotal", this.subtotal);
        return returnObject;
    }
}