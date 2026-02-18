package com.example.forohub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forohub.dto.TopicoDto;
import com.example.forohub.dto.TopicoResponse;
import com.example.forohub.dto.UsuarioDto;
import com.example.forohub.dto.UsuarioResponse;
import com.example.forohub.entity.Topico;
import com.example.forohub.entity.Usuario;
import com.example.forohub.repository.TopicoRepository;
import com.example.forohub.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        var lista = usuarioRepository.findAll().stream()
                .map(UsuarioResponse::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        var nombreUsuario = usuarioDto.getNombreUsuario();

        if (usuarioRepository.findByNombreUsuario(nombreUsuario) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe un usuario con el mismo nombre de usuario");
        }

        Usuario usuario = Usuario.builder()
                .nombreUsuario(nombreUsuario)
                .clave(usuarioDto.getClave())
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(usuarioGuardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> detalleUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(new UsuarioResponse(usuario.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDto usuarioDto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);  
      
        if (optionalUsuario.isPresent()) {
            Usuario u = optionalUsuario.get();
            u.setNombreUsuario(usuarioDto.getNombreUsuario());
            u.setClave(usuarioDto.getClave());
            
            return ResponseEntity.ok(new UsuarioResponse(usuarioRepository.save(u)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {        
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
