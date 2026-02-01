package com.fundamentos_SW.demo.servicio;

import com.fundamentos_SW.demo.model.Usuario;
import com.fundamentos_SW.demo.model.Rol;
import com.fundamentos_SW.demo.repositorio.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class RegistroUsuarioHU7 {

    @Autowired
    private UsuarioRepo usuarioRepo;

    private static final String DOMINIO_CORREO = "@javeriana.edu.co";

    @Transactional
    public String registrarUsuario(Usuario nuevoUsuario) {
        // Validación de campos obligatorios
        if (!validarCamposObligatorios(nuevoUsuario)) {
            return "Todos los campos son obligatorios.";
        }

        // Validación de formato de correo
        if (!validarCorreoInstitucional(nuevoUsuario.getCorreo())) {
            return "El correo debe ser institucional (@javeriana.edu.co).";
        }

        // Validación de unicidad de datos
        if (usuarioRepo.existsByCorreo(nuevoUsuario.getCorreo())) {
            return "Ya existe un usuario con ese correo.";
        }

        if (usuarioRepo.existsByCedula(nuevoUsuario.getCedula())) {
            return "Ya existe un usuario con esa cédula.";
        }

        if (usuarioRepo.existsByIdInstitucional(nuevoUsuario.getIdInstitucional())) {
            return "Ya existe un usuario con ese ID institucional.";
        }

        // Asignar valores por defecto si es necesario
        if (nuevoUsuario.getRol() == null) {
            nuevoUsuario.setRol(Rol.Comprador); // Rol por defecto
        }

        // Guardar el usuario
        usuarioRepo.save(nuevoUsuario);
        return "Registro exitoso.";
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepo.findByCorreo(correo);
    }

    public Optional<Usuario> buscarPorIdInstitucional(String idInstitucional) {
        return usuarioRepo.findByIdInstitucional(idInstitucional);
    }

    public Optional<Usuario> buscarPorCedula(String cedula) {
        return usuarioRepo.findByCedula(cedula);
    }

    public Optional<Usuario> autenticarUsuario(String correo, String contrasenia) {
        Optional<Usuario> usuarioOpt = usuarioRepo.findByCorreo(correo);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getContrasenia().equals(contrasenia)) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    // Métodos de validación privados
    private boolean validarCamposObligatorios(Usuario usuario) {
        return StringUtils.hasText(usuario.getNombre()) &&
                StringUtils.hasText(usuario.getContrasenia()) &&
                StringUtils.hasText(usuario.getCedula()) &&
                StringUtils.hasText(usuario.getCorreo()) &&
                StringUtils.hasText(usuario.getFacultad()) &&
                StringUtils.hasText(usuario.getIdInstitucional()) &&
                usuario.getRol() != null;
    }

    private boolean validarCorreoInstitucional(String correo) {
        return StringUtils.hasText(correo) &&
                correo.toLowerCase().endsWith(DOMINIO_CORREO);
    }
}

