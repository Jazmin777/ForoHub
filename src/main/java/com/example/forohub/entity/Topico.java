package com.example.forohub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, unique = true)
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private Boolean status = true;

    @NotBlank(message = "El autor es obligatorio")
    @Column(nullable = false)
    private String autor;

    @NotBlank(message = "El curso es obligatorio")
    @Column(nullable = false)
    private String curso;

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
        if (status == null) {
            status = true;
        }
    }
}
