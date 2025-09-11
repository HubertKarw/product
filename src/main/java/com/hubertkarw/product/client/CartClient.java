package com.hubertkarw.product.client;

import com.hubertkarw.product.model.CartItemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cart")
public interface CartClient {

    @PostMapping("/carts/{id}/items")
    public CartItemRequest addItemToCart(@PathVariable long id, @RequestBody CartItemRequest request);
}
