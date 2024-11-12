package kopo.delivery.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String userID;
    private String userPW;
    private String userName;
    private String userPhone;
    private String userEmail;
}
