package com.example.forohub.dto;

import java.time.LocalDateTime;
import com.example.forohub.entity.Topico;

public record TopicoResponse(
    String titulo,
    String mensaje,
    LocalDateTime fechaCreacion,
    Boolean status,
    String autor,
    String curso
) {
    // Constructor
    public TopicoResponse {
        if (fechaCreacion == null) fechaCreacion = LocalDateTime.now();
        if (status == null) status = Boolean.TRUE;
    }

    // Constructor auxiliar desde la entidad Topico
    public TopicoResponse(Topico t) {
        this(t.getTitulo(), t.getMensaje(), t.getFechaCreacion(), t.getStatus(), t.getAutor(), t.getCurso());
    }
}