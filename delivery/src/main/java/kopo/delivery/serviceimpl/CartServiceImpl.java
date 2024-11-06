package kopo.delivery.serviceimpl;

import jakarta.transaction.Transactional;
import kopo.delivery.dto.CartDTO;
import kopo.delivery.dto.StoreDTO;
import kopo.delivery.dto.StoreMenuDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.entity.Store;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.repository.CartRepo;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final StoreMenuRepo storeMenuRepo;

    @Override
    @Transactional
    public Cart addCart(StoreMenuDTO menu) {
        //store_menu ID로 메뉴들 조회하기
        StoreMenu storeMenu = storeMenuRepo.findById(menu.getId()).orElse(null);

        Cart cart = new Cart();
        cart.setMenu(storeMenu);
        cart.setAddTime(LocalDateTime.now());
        return cartRepo.save(cart);
    }

    @Override
    public  List<CartDTO> getCartItems() {
        List<Object[]> cartList = cartRepo.countCartMenu();

        return cartList.stream()
                .map(cart -> {
                   Long menuId = (Long) cart[0];
                   String menuName = (String) cart[1];
                   Integer menuAmount = (Integer) cart[2];
                   Long count = (Long) cart[3];

                   StoreMenuDTO storeMenuDTO = new StoreMenuDTO(menuId, menuName, menuAmount);

                   return CartDTO.builder()
                           .menu(storeMenuDTO)
                           .count(count)
                           .build();
                })
                .collect(Collectors.toList());
    }

    public String getMaxCartId(Long menuId){
        String cartId = String.valueOf(cartRepo.findMaxCartIdByMenuId(menuId));
        return cartId;
    }

    @Override
    @Transactional
    public List<CartDTO> cartItemsMinus(Long cartId) {
        cartRepo.deleteCartMenu(cartId);
        return getCartItems();
    }


}
