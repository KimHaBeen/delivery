package kopo.delivery.controller;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/cart")
@Controller
public class CartContoller {

    final CartService cartService;
    private final SimpMessagingTemplate messagingTemplate;

    @ResponseBody
    @PostMapping("/addCart")
    public Map<String, Object> addCart(@RequestBody CartDTO cartDTO) {
        System.out.println(cartDTO);
        System.out.println("넘어옴");
        Cart cart = cartService.addCart(cartDTO); //값 저장
        System.out.println("저장됨");

        Map<String, Object> response = new HashMap<>();
        response.put("id", cart.getId());
        response.put("menuName", cart.getMenu().getMenuName());
        response.put("time", cart.getAddTime());

        messagingTemplate.convertAndSend("/topic/store", cartService.getCartItems());
        return response;
    }

}
