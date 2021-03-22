package com.carlostambascia.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AuthRequest implements Serializable {
    private String userName;
    private String password;
}
