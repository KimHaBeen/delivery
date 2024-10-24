package kopo.delivery.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class CartDTO {

    private Long id;
    @Setter private StoreMenuDTO menu;
    @Setter private int quantity;
    private final LocalDateTime addTime = LocalDateTime.now();
}
