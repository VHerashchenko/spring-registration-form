package com.vh.springregistrationform.dto;

import com.vh.springregistrationform.model.Role;
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