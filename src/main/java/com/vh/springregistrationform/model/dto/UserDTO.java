package com.vh.springregistrationform.model.dto;

import com.vh.springregistrationform.model.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTO {

    private Integer id;

    private String username;

    private String password;

    private String passwordConfirm;

    private Role role;
}