package com.uniminuto.clinica.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "login.security")
public class LoginSecurityConfig {
    private int maxIntentosFallidos = 3;
    private int minutosBloqueo = 5;
    private boolean habilitarBloqueo = true;
}
