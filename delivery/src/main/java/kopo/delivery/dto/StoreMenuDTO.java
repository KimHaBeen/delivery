package kopo.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class StoreMenuDTO {

    private Long id;
    private Long storeId;
    private String menuName;
    private int menuAmount;
    private String url;


}
