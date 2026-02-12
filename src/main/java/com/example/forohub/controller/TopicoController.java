package com.example.forohub.controller;

import com.example.forohub.dto.TopicoDto;
import com.example.forohub.dto.TopicoResponse;
import com.example.forohub.entity.Topico;
import com.example.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearTopico(@Valid @RequestBody TopicoDto topicoDto) {
        
        // Validar que no exista un tópico con el mismo título y mensaje
        if (topicoRepository.existsByTituloAndMensaje(topicoDto.getTitulo(), topicoDto.getMensaje())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe un tópico con el mismo título y mensaje");
        }

        // Crear el tópico a partir del DTO
        Topico topico = Topico.builder()
                .titulo(topicoDto.getTitulo())
                .mensaje(topicoDto.getMensaje())
                .autor(topicoDto.getAutor())
                .curso(topicoDto.getCurso())
                .build();

        // Guardar en la base de datos
        Topico topicoGuardado = topicoRepository.save(topico);

        TopicoResponse resp = new TopicoResponse(topicoGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }


    @GetMapping
    @Transactional
    public ResponseEntity<?> listarTopicos() {
        return ResponseEntity.ok(topicoRepository.findAll().stream().map(TopicoResponse::new).toList());
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> detalleTopico(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(TopicoResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> actualizarTopico(@PathVariable Long id, @Valid @RequestBody TopicoDto topicoDto) {
      Optional<Topico> topico = topicoRepository.findById(id);  
      
      if (topico.isPresent()) {
          Topico t = topico.get();
          t.setTitulo(topicoDto.getTitulo());
          t.setMensaje(topicoDto.getMensaje());
          t.setAutor(topicoDto.getAutor());
          t.setCurso(topicoDto.getCurso());
          topicoRepository.save(t);
          return ResponseEntity.ok(new TopicoResponse(t));
      } else {
          return ResponseEntity.notFound().build();
      }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> eliminarTopico(@PathVariable Long id) {        
        if ((!topicoRepository.existsById(id))) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
