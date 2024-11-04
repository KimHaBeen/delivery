package kopo.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class StoreDTO {

            private Long storeID;
    @Setter private String storeName;
    @Setter private int storeMinAmount;
    @Setter private String category;

    public StoreDTO(String storeName) {
        this.storeName = storeName;
    }

}
