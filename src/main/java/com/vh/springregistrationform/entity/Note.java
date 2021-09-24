package com.vh.springregistrationform.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Note {

    private Integer id;

    private String username;

    private String password;

    private String passwordConfirm;
}