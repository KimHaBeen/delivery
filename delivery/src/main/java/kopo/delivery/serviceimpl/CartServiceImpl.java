package kopo.delivery.serviceimpl;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.dto.StoreDTO;
import kopo.delivery.dto.StoreMenuDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.repository.CartRepo;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final StoreMenuRepo storeMenuRepo;

    @Override
    public Cart addCart(CartDTO cartDTO) {
        //store_menu ID로 메뉴들 조회하기
        StoreMenu menu = storeMenuRepo.findById(cartDTO.getMenu().getId())
                .orElseThrow(() -> new RuntimeException("Menu not found" + cartDTO.getMenu().getId()));

        Cart cartentity = new Cart();
        cartentity.setMenu(menu);
        cartentity.setQuantity(cartDTO.getQuantity());

        return cartRepo.save(cartentity);
    }

    @Override
    public  List<CartDTO> getCartItems() {
        List<Cart> cartList = cartRepo.findAll();

        return cartList.stream()
                .map(cart -> {
                    StoreMenu storeMenu = cart.getMenu();
                    StoreDTO storeDTO = new StoreDTO(storeMenu.getStore().getStoreName());
                    StoreMenuDTO storeMenuDTO = new StoreMenuDTO(
                            storeMenu.getId(),
                            storeDTO,
                            storeMenu.getMenuName(),
                            storeMenu.getMenuAmount()
                    );

                    return new CartDTO(
                            cart.getId(),
                            storeMenuDTO,
                            cart.getQuantity(),
                            cart.getAddTime()
                    );
                })
                .collect(Collectors.toList());
    }


}
