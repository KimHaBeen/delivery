package kopo.delivery.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StoreMenuDTO {

    private Long id;
    private StoreDTO store;
    private String menuName;
    private int menuAmount;
    private String url;

    public StoreMenuDTO(Long id, StoreDTO storeDTO, String menuName, int menuAmount) {
        this.id = id;
        this.store = storeDTO;
        this.menuName = menuName;
        this.menuAmount = menuAmount;
    }
}
