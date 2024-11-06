package kopo.delivery.controller;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.dto.StoreDTO;
import kopo.delivery.dto.StoreMenuDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/cart")
@Controller
public class CartContoller {

    final CartService cartService;
    private final SimpMessagingTemplate messagingTemplate;

    @ResponseBody
    @PostMapping("/addCart")
    public Map<String, Object> addCart(@RequestBody StoreMenuDTO menu) {
        System.out.println(menu);
        Cart cart = cartService.addCart(menu); //값 저장
        System.out.println("저장됨");

        Map<String, Object> response = new HashMap<>();
        response.put("id", cart.getId());
        response.put("menuName", cart.getMenu().getMenuName());
        response.put("time", cart.getAddTime());

        messagingTemplate.convertAndSend("/topic/cartUpdates", cartService.getCartItems());
        return response;
    }

    @ResponseBody
    @GetMapping("/cartMenus")
    public List<CartDTO> cartMenus() {
        System.out.println(cartService.getCartItems());
        return cartService.getCartItems();
    }

    @MessageMapping("/minusBtn")
    @SendTo("/topic/cartUpdates")
    public List<CartDTO> minusBtn(Map<String, Object> map) {
        String action = (String) map.get("action");
        Long menuId = ((Number) map.get("id")).longValue();
        String cartId = cartService.getMaxCartId(menuId);
        System.out.println(cartId);
        if ("minusBtn".equals(action)) {
            cartService.cartItemsMinus(Long.valueOf(cartId));
        }
        return cartService.getCartItems();
    }

}
