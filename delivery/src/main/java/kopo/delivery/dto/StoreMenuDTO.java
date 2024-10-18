package kopo.delivery.dto;

import kopo.delivery.entity.Store;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class StoreMenuDTO {

    private Long id;
    private Store store;
    private String menuName;
    private int menuAmount;
    private String url;


}
