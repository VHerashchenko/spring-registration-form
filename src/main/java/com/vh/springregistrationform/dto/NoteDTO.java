package com.vh.springregistrationform.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NoteDTO {

    private String username;

    private String password;

    private String passwordConfirm;
}