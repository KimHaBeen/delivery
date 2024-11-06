package kopo.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long id;
    private StoreMenuDTO menu;
    private Long count;
    @Builder.Default
    private LocalDateTime addTime = LocalDateTime.now();


}
