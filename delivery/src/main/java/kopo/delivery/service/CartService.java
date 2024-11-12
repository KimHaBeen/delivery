package kopo.delivery.service;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.dto.StoreMenuDTO;
import kopo.delivery.entity.Cart;
import kopo.delivery.entity.User;

import java.util.List;
import java.util.Map;

public interface CartService {

    //Cart addCart(StoreMenuDTO storeMenuDTO);

    List<CartDTO> getCartItems(User user);

    String getMaxCartId(Long menuId);

    List<CartDTO> cartItemsMinus(Long cartId, User user);

    Cart cartItemsPlus(Long cartId, User user);

    void removeCartItem(Long id);

    Map<String, Object> handleAddCart(StoreMenuDTO menu, User user);
}
