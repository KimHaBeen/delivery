package kopo.delivery.service;

import kopo.delivery.dto.CartDTO;
import kopo.delivery.entity.Cart;

public interface CartService {

    Cart addCart(CartDTO cartDTO);
}
