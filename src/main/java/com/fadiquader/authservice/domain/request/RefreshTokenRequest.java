package com.fadiquader.authservice.domain.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenRequest {
    @NotEmpty
    private String token;
}
