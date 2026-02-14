package com.example.forohub.controller;

import com.example.forohub.dto.TopicoDto;
import com.example.forohub.dto.TopicoResponse;
import com.example.forohub.entity.Topico;
import com.example.forohub.entity.Usuario;
import com.example.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearTopico(@Valid @RequestBody TopicoDto topicoDto) {
        
        if (topicoRepository.existsByTituloAndMensaje(topicoDto.getTitulo(), topicoDto.getMensaje())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe un tópico con el mismo título y mensaje");
        }

        Usuario usuarioLogueado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Topico topico = Topico.builder()
                .titulo(topicoDto.getTitulo())
                .mensaje(topicoDto.getMensaje())
                .autor(usuarioLogueado.getNombreUsuario())
                .curso(topicoDto.getCurso())
                .build();

        Topico topicoGuardado = topicoRepository.save(topico);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TopicoResponse(topicoGuardado));
    }

    @GetMapping
    public ResponseEntity<List<TopicoResponse>> listarTopicos() {
        var lista = topicoRepository.findAll().stream()
                .map(TopicoResponse::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> detalleTopico(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(t -> ResponseEntity.ok(new TopicoResponse(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponse> actualizarTopico(@PathVariable Long id, @Valid @RequestBody TopicoDto topicoDto) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);  
      
        if (optionalTopico.isPresent()) {
            Topico t = optionalTopico.get();
            t.setTitulo(topicoDto.getTitulo());
            t.setMensaje(topicoDto.getMensaje());
            t.setCurso(topicoDto.getCurso());
            
            return ResponseEntity.ok(new TopicoResponse(t));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {        
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}