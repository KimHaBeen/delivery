package kopo.delivery.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {

    private Long storeID;
    private String storeName;
    private int storeMinAmount;
    private String category;


}
