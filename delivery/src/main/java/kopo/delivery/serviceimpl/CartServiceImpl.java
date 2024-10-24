package kopo.delivery.serviceimpl;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.entity.StoreMenu;
import kopo.delivery.repository.CartRepo;
import kopo.delivery.repository.StoreMenuRepo;
import kopo.delivery.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;
    private final StoreMenuRepo storeMenuRepo;

    @Override
    public Cart addCart(CartDTO cartDTO) {
        //store_menu ID로 메뉴들 조회하기
        StoreMenu menu = storeMenuRepo.findById(cartDTO.getId())
                .orElseThrow(()-> new IllegalArgumentException("ID를 찾을 수 없습니다."));

        Cart cartentity = new Cart();
        cartentity.setMenu(menu);
        cartentity.setQuantity(cartDTO.getQuantity());

        return cartRepo.save(cartentity);
    }


}
