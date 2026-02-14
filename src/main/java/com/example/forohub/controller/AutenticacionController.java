package com.example.forohub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.forohub.dto.DatosAutenticacionUsuario;
import com.example.forohub.dto.DatosJWTToken;
import com.example.forohub.entity.Usuario;
import com.example.forohub.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired // <--- Agregamos esto
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService; //

    @SuppressWarnings("rawtypes")
    @PostMapping
    public ResponseEntity iniciarSesion(@RequestBody @Valid DatosAutenticacionUsuario datos){
        var authenticationToken = new UsernamePasswordAuthenticationToken(datos.nombreUsuario(), datos.clave());
        var usuarioAutenticado = manager.authenticate(authenticationToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
