package com.fundamentos_SW.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Contrasenia")
    private String contrasenia; // o contrasena si prefieres

    @Column(name = "Cedula", unique = true)
    private String cedula;

    @Column(name = "Correo", unique = true)
    private String correo;

    @Column(name = "Facultad")
    private String facultad;

    @Column(name = "ID_Institucional", unique = true)
    private String idInstitucional;

    @Enumerated(EnumType.STRING)
    @Column(name = "Rol")
    private Rol rol;

    // Getters y setters

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getIdInstitucional() {
        return idInstitucional;
    }

    public void setIdInstitucional(String idInstitucional) {
        this.idInstitucional = idInstitucional;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

