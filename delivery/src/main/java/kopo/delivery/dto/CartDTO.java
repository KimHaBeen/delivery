package kopo.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class CartDTO {

            private Long id;
    @Setter private StoreMenuDTO menu;
    @Setter private int quantity;
            private LocalDateTime addTime = LocalDateTime.now();

    public CartDTO(Long id, StoreMenuDTO menu, int quantity, LocalDateTime addTime) {
        this.id = id;
        this.menu = menu;
        this.quantity = quantity;
        this.addTime = addTime;
    }

}
