package com.fadiquader.authservice.domain.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
