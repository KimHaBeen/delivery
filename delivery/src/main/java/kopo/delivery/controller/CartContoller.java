package kopo.delivery.controller;

import jakarta.servlet.http.HttpSession;
import kopo.delivery.dto.CartDTO;
import kopo.delivery.dto.StoreMenuDTO;
import kopo.delivery.entity.User;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
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
    private final StoreMenuRepo storeMenuRepo;


    @ResponseBody
    @PostMapping("/addCart")
    public ResponseEntity<Map<String, Object>> addCart(@RequestBody StoreMenuDTO menu, HttpSession session) {
        System.out.println(menu);
        User user = (User) session.getAttribute("user");
        Map<String, Object> response = new HashMap<>();
        // 세션에서 현재 로그인된 User 객체를 가져옴
        if (user == null) {
            response.put("error", "로그인된 사용자 정보가 없습니다.(/addCart)");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        // 서비스 메서드를 호출해서 장바구니에 추가 처리
        response = cartService.handleAddCart(menu, user);

        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @DeleteMapping("/removeCartItem")
    public Map<String, Object> removeCartItem(@RequestBody StoreMenuDTO menu) {
        Map<String, Object> response = new HashMap<>();
        try {
            cartService.removeCartItem(menu.getId());  // storeId를 사용해 해당 항목을 삭제
            response.put("success", "장바구니 항목이 삭제되었습니다.");
        } catch (Exception e) {
            response.put("error", "장바구니 항목 삭제 중 오류가 발생했습니다.");
        }
        return response;
    }

    /*@ResponseBody
    @PostMapping("/addCart")
    public Map<String, Object> addCart(@RequestBody StoreMenuDTO menu) {
        System.out.println(menu);

        StoreMenu storeMenu = storeMenuRepo.findById(menu.getId()).orElse(null);

        CartDTO existingCart = cartService.getCartItems().stream()
                .filter(cartDTO -> cartDTO.getStoreID().equals(menu.getStore().getStoreID())) // storeID로 비교
                .findFirst()
                .orElse(null);

        if (existingCart != null && !existingCart.getMenu().getStore().getStoreID().equals(menu.getStore().getStoreID())) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "장바구니에는 같은 가게의 메뉴만 담을 수 있습니다. 선택하신 메뉴를 담을 시 이전 메뉴는 삭제됩니다.");
            return response;
        }
        // 장바구니에 기존 메뉴 삭제
        if (existingCart != null) {
            cartService.removeCartItem(existingCart.getId()); // 기존 메뉴 삭제
        }

        Cart cart = cartService.addCart(menu); //값 저장
        System.out.println("저장됨");

        Map<String, Object> response = new HashMap<>();
        response.put("id", cart.getId());
        response.put("menuName", cart.getMenu().getMenuName());
        response.put("time", cart.getAddTime());

        messagingTemplate.convertAndSend("/topic/cartUpdates", cartService.getCartItems());
        return response;
    }*/

    @ResponseBody
    @GetMapping("/cartMenus")
    public List<CartDTO> cartMenus(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new IllegalStateException("로그인된 사용자 정보가 없습니다.(/cartMenus)");
        }
        System.out.println(cartService.getCartItems(user));
        return cartService.getCartItems(user);
    }

    @MessageMapping("/minusBtn")
    @SendTo("/topic/cartUpdates")
    public List<CartDTO> minusBtn(Map<String, Object> map, SimpMessageHeaderAccessor headerAccessor) {
        String action = (String) map.get("action");
        Long menuId = ((Number) map.get("id")).longValue();
        String cartId = cartService.getMaxCartId(menuId);

        User user = (User) headerAccessor.getSessionAttributes().get("user");
        if (user == null) {
            throw new IllegalStateException("로그인된 사용자 정보가 없습니다.");
        }

        System.out.println(cartId);
        if ("minusBtn".equals(action)) {
            cartService.cartItemsMinus(Long.valueOf(cartId), user);
        }
        return cartService.getCartItems(user);
    }

    @MessageMapping("/plusBtn")
    @SendTo("/topic/cartUpdates")
    public List<CartDTO> plusBtn(Map<String, Object> map, SimpMessageHeaderAccessor headerAccessor) {
        String action = (String) map.get("action");
        Long menuId = ((Number) map.get("id")).longValue();

        User user = (User) headerAccessor.getSessionAttributes().get("user");
        if (user == null) {
            throw new IllegalStateException("로그인된 사용자 정보가 없습니다.");
        }

        if ("plusBtn".equals(action)) {
            cartService.cartItemsPlus(menuId, user);
        }
        return cartService.getCartItems(user);
    }

}
