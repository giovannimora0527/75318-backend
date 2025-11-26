package com.uniminuto.clinica.security;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.service.UsuarioService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleChecker {

    private static UsuarioService usuarioServiceStatic;

    @Autowired
    public RoleChecker(UsuarioService usuarioService) {
        RoleChecker.usuarioServiceStatic = usuarioService;
    }

    /**
     * Verifica si el usuario actual tiene alguno de los roles permitidos.
     * Si no tiene, lanza BadRequestException.
     *
     * @param rolesPermitidos Roles permitidos (ADMIN, MEDICO, PACIENTE)
     * @throws BadRequestException si no tiene permiso
     */
    public static void checkRole(String... rolesPermitidos) throws BadRequestException {
        // Aquí obtienes el usuario autenticado
        Usuario usuarioActual = usuarioServiceStatic.getUsuarioLogeado(); // Debes implementar este método

        if (usuarioActual == null || usuarioActual.getRol() == null) {
            throw new BadRequestException("Usuario no autenticado o sin rol");
        }

        for (String rol : rolesPermitidos) {
            if (usuarioActual.getRol().equalsIgnoreCase(rol)) {
                return; // Tiene rol permitido
            }
        }

        // Si no tiene ninguno de los roles permitidos
        throw new BadRequestException("Acceso denegado: no tiene el rol necesario");
    }
}
