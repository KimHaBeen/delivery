package kopo.delivery.serviceimpl;

import jakarta.transaction.Transactional;
import kopo.delivery.dto.CartDTO;
import kopo.delivery.dto.StoreDTO;
import kopo.delivery.dto.StoreMenuDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.entity.User;
import kopo.delivery.repository.CartRepo;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final StoreMenuRepo storeMenuRepo;
    private final SimpMessagingTemplate messagingTemplate;


    /*@Override
    @Transactional
    public Cart addCart(StoreMenuDTO menu) {
        System.out.println("여기로왔나요?");
        //store_menu ID로 메뉴들 조회하기
        StoreMenu storeMenu = storeMenuRepo.findById(menu.getId()).orElse(null);

        if (storeMenu == null) {
            // StoreMenu가 없으면 null 처리
            throw new IllegalArgumentException("The menu item does not exist.");
        }

        // store가 null인 경우를 처리
        if (storeMenu.getStore() == null) {
            throw new IllegalArgumentException("Store information is missing for this menu.");
        }
        Cart cart = new Cart();
        cart.setMenu(storeMenu);
        cart.setAddTime(LocalDateTime.now());
        cart.setStoreID(storeMenu.getStore().getStoreID());
        return cartRepo.save(cart);
    }*/

    @Override
    public Map<String, Object> handleAddCart(StoreMenuDTO menu, User user) {
        System.out.println("여기는왔니?");
        StoreMenu storeMenu = storeMenuRepo.findById(menu.getId()).orElse(null);

        if (storeMenu == null) {
            throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
        }

        Long storeID = storeMenu.getStore().getStoreID();
        CartDTO differentStoreCart = getDifferentStoreCart(storeID, user); // 다른 storeID 검사

        Map<String, Object> response = new HashMap<>();

        if (differentStoreCart != null) {
            // 다른 storeID가 있을 때 에러 메시지와 기존 cartId 반환
            response.put("error", "장바구니에는 같은 가게의 메뉴만 담을 수 있습니다. 선택하신 메뉴를 담을 시 이전 메뉴는 삭제됩니다.");
            response.put("existingCartId", differentStoreCart.getId());
            return response;
        }

        // 같은 storeID일 경우 기존 항목 삭제 없이 추가
        Cart cart = addCart(storeMenu, user);
        messagingTemplate.convertAndSend("/topic/cartUpdates", getCartItems(user));

        response.put("id", cart.getId());
        response.put("menuName", cart.getMenu().getMenuName());
        response.put("time", cart.getAddTime());
        return response;
    }

    @Override
    public  List<CartDTO> getCartItems(User user) {
        List<Object[]> cartList = cartRepo.countCartMenu(user);

        return cartList.stream()
                .map(cart -> {
                   Long menuId = (Long) cart[0];
                   String menuName = (String) cart[1];
                   Integer menuAmount = (Integer) cart[2];
                   Long count = (Long) cart[3];
                   Long storeID = (Long) cart[4];

                    StoreDTO storeDTO = new StoreDTO(storeID);
                    StoreMenuDTO storeMenuDTO = new StoreMenuDTO(menuId, menuName, menuAmount, storeID);

                    if (storeMenuDTO.getStore() == null) {
                        System.err.println("Store is null for menuId: " + menuId);
                    }

                   return CartDTO.builder()
                           .menu(storeMenuDTO)
                           .count(count)
                           .storeID(storeID)
                           .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getMaxCartId(Long menuId){
        String cartId = String.valueOf(cartRepo.findMaxCartIdByMenuId(menuId));
        return cartId;
    }

    @Override
    @Transactional
    public List<CartDTO> cartItemsMinus(Long cartId, User user) {
        cartRepo.deleteCartMenu(cartId);
        return getCartItems(user);
    }

    @Override
    @Transactional
    public Cart cartItemsPlus(Long menuId, User user){
        StoreMenu storeMenu = storeMenuRepo.findById(menuId).orElse(null);
        Cart cart = new Cart();
        cart.setMenu(storeMenu);
        cart.setAddTime(LocalDateTime.now());
        cart.setStoreID(storeMenu.getStore().getStoreID());
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    @Override
    @Transactional
    public void removeCartItem(Long id) {
        cartRepo.deleteAll();
    }

    @Transactional
    protected Cart addCart(StoreMenu storeMenu, User user) {

        Cart cart = new Cart();
        cart.setMenu(storeMenu);
        cart.setAddTime(LocalDateTime.now());
        cart.setStoreID(storeMenu.getStore().getStoreID());
        cart.setUser(user);
        return cartRepo.save(cart);
    }

    private CartDTO getDifferentStoreCart(Long storeID, User user) {
        return getCartItems(user).stream()
                .filter(cartDTO -> !cartDTO.getStoreID().equals(storeID)) // 다른 storeID가 있는지 확인
                .findFirst()
                .orElse(null);
    }

}
