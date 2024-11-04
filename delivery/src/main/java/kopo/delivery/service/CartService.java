package kopo.delivery.service;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Cart;

import java.util.List;

public interface CartService {

    Cart addCart(CartDTO cartDTO);

    List<CartDTO> getCartItems();
}
