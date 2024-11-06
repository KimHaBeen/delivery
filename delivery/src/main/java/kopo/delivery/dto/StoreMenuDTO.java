package kopo.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonToken;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreMenuDTO {

    @JsonProperty("menuId")
    private Long id;
    private StoreDTO store;
    private String menuName;
    private int menuAmount;
    private String url;


    public StoreMenuDTO(Long id, String menuName, int menuAmount) {
        this.id = id;
        this.menuName = menuName;
        this.menuAmount = menuAmount;
    }
}
