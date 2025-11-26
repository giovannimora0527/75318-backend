package com.uniminuto.clinica.utils;

import com.uniminuto.clinica.entity.Usuario;

public class RoleChecker {

    /**
     * Verifica si el usuario tiene alguno de los roles permitidos.
     *
     * @param usuario Usuario que se va a verificar
     * @param rolesPermitidos Roles permitidos (pueden ser varios)
     * @return true si tiene alguno de los roles permitidos, false si no
     */
    public static boolean checkRole(Usuario usuario, String... rolesPermitidos) {
        if (usuario == null || usuario.getRol() == null) {
            return false;
        }
        for (String rol : rolesPermitidos) {
            if (usuario.getRol().equalsIgnoreCase(rol)) {
                return true;
            }
        }
        return false;
    }
}
