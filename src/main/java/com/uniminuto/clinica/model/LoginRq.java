package com.uniminuto.clinica.model;

import lombok.Data;

@Data
public class LoginRq {
    private String username;
    private String password;
    private String ip = "N/A";
}
