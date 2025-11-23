package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.ConfiguracionSistema;
import com.uniminuto.clinica.repository.ConfiguracionSistemaRepository;
import com.uniminuto.clinica.service.ConfiguracionSistemaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionSistemaServiceImpl implements ConfiguracionSistemaService {
    
    @Autowired
    private ConfiguracionSistemaRepository repo;
    
    @Override
    public Integer getIntentosLoginMax() {
        return Integer.parseInt(getValorConfiguracion("INTENTOS_LOGIN_MAX", "3"));
    }
    
    @Override
    public Integer getTiempoBloqueoMinutos() {
        return Integer.parseInt(getValorConfiguracion("TIEMPO_BLOQUEO_MINUTOS", "5"));
    }
    
    @Override
    public Integer getTokenExpiracionHoras() {
        return Integer.parseInt(getValorConfiguracion("TOKEN_EXPIRACION_HORAS", "24"));
    }
    
    @Override
    public Boolean isEmailRecuperacionHabilitado() {
        return Boolean.parseBoolean(getValorConfiguracion("EMAIL_RECUPERACION_HABILITADO", "true"));
    }
    
    @Override
    public String getValorConfiguracion(String clave, String valorPorDefecto) {
        Optional<ConfiguracionSistema> config = repo.findByClave(clave);
        return config.map(ConfiguracionSistema::getValor).orElse(valorPorDefecto);
    }
}