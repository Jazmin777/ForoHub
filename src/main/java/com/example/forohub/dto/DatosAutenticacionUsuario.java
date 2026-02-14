package com.example.forohub.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
    @NotBlank String nombreUsuario, 
    @NotBlank String clave
) {
}