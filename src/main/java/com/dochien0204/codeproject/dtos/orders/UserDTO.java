package com.dochien0204.codeproject.dtos.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;

    private String userName;

    @Nationalized
    private String address;

    private String email;

    private String phone;

    @Nationalized
    private String fullName;

}
