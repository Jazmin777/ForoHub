package com.example.forohub.dto;

import com.example.forohub.entity.Usuario;

public record UsuarioResponse(
    Long id,
    String nombreUsuario,
    String clave
) {
    public UsuarioResponse {
        if (nombreUsuario == null) nombreUsuario = "";
        if (clave == null) clave = "";
    }

    public UsuarioResponse(Usuario usuario) {
        this(usuario.getId(), usuario.getNombreUsuario(), usuario.getClave());
    }
}
