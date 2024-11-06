package kopo.delivery.service;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.dto.StoreMenuDTO;
import kopo.delivery.entity.Cart;

import java.util.List;

public interface CartService {

    Cart addCart(StoreMenuDTO storeMenuDTO);

    List<CartDTO> getCartItems();

    String getMaxCartId(Long menuId);

    List<CartDTO> cartItemsMinus(Long cartId);
}
