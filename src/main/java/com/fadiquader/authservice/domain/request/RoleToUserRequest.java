package com.fadiquader.authservice.domain.request;

import lombok.Data;

@Data
public class RoleToUserRequest {
    private String username;
    private String rolename;
}
