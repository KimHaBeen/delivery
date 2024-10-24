package kopo.delivery.controller;

import com.google.gson.JsonObject;
import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class CartContoller {

    final CartService cartService;

    @ResponseBody
    @PostMapping("/addcart")
    public Map<String, Object> addCart(@RequestBody CartDTO cartDTO) {
        Cart cart = cartService.addCart(cartDTO);
        StoreMenu storeMenu = new StoreMenu();

        Map<String, Object> response = new HashMap<>();
        response.put("id", cart.getId());
        response.put("menuname", cart.getMenu().getMenuName());
        response.put("time", cart.getAddTime());
        return response;
    }
}
