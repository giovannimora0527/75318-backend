package com.uniminuto.clinica.service;

public interface ConfiguracionSistemaService {
    Integer getIntentosLoginMax();
    Integer getTiempoBloqueoMinutos();
    Integer getTokenExpiracionHoras();
    Boolean isEmailRecuperacionHabilitado();
    String getValorConfiguracion(String clave, String valorPorDefecto);
}