package com.s1.sistemaGA_Bodegas.auth;

import com.s1.sistemaGA_Bodegas.configuracion.JwtServicio;
import com.s1.sistemaGA_Bodegas.excepciones.BusinessRuleException;
import com.s1.sistemaGA_Bodegas.modelo.Admin;
import com.s1.sistemaGA_Bodegas.modelo.Empleado;
import com.s1.sistemaGA_Bodegas.repositorio.AdminRepositorio;
import com.s1.sistemaGA_Bodegas.repositorio.EmpleadoRepositorio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControlador {

    private final AdminRepositorio adminRepositorio;
    private final EmpleadoRepositorio empleadoRepositorio;
    private final JwtServicio jwtServicio;
    private final PasswordEncoder passwordEncoder;

    //login
    @PostMapping("/login")
    public ResponseEntity<LoginRespuesta> login(@Valid @RequestBody LoginSolicitud dto) {

        //busca primero el admin
        Admin admin = adminRepositorio.findByUsuario(dto.usuario()).orElse(null);
        if (admin != null) {
            if (!passwordEncoder.matches(dto.contrasena(), admin.getContrasena())) {
                throw new BusinessRuleException("Credenciales incorrectas");
            }
            String token = jwtServicio.generateToken(admin.getUsuario(), "ADMIN");
            return ResponseEntity.ok(new LoginRespuesta(token, "Bearer", admin.getUsuario(), "ADMIN"));
        }

        //busca el empleado
        Empleado empleado = empleadoRepositorio.findByUsuario(dto.usuario()).orElse(null);
        if (empleado != null) {
            if (!passwordEncoder.matches(dto.contrasena(), empleado.getContrasena())) {
                throw new BusinessRuleException("Credenciales incorrectas");
            }
            String token = jwtServicio.generateToken(empleado.getUsuario(), "EMPLEADO");
            return ResponseEntity.ok(new LoginRespuesta(token, "Bearer", empleado.getUsuario(), "EMPLEADO"));
        }

        throw new BusinessRuleException("Usuario no encontrado: " + dto.usuario());
    }

    //registrar
    @PostMapping("/register")
    public ResponseEntity<LoginRespuesta> register(@Valid @RequestBody RegisterSolicitud dto) {

        // verifica que no exista ni en admin ni en empleado
        if (adminRepositorio.existsByUsuario(dto.usuario()) ||
                empleadoRepositorio.existsByUsuario(dto.usuario())) {
            throw new BusinessRuleException("El usuario ya existe: " + dto.usuario());
        }
        String token;
        if (dto.rol().equals("ADMIN")) {
            Admin admin = new Admin();
            admin.setNombre(dto.nombre());
            admin.setDocumento(dto.documento());
            admin.setEdad(dto.edad());
            admin.setCorreo(dto.correo());
            admin.setUsuario(dto.usuario());
            admin.setContrasena(passwordEncoder.encode(dto.contrasena()));
            adminRepositorio.save(admin);
            token = jwtServicio.generateToken(admin.getUsuario(), "ADMIN");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new LoginRespuesta(token, "Bearer", admin.getUsuario(), "ADMIN"));
        }

        Empleado empleado = new Empleado();
        empleado.setNombre(dto.nombre());
        empleado.setDocumento(dto.documento());
        empleado.setEdad(dto.edad());
        empleado.setCorreo(dto.correo());
        empleado.setUsuario(dto.usuario());
        empleado.setContrasena(passwordEncoder.encode(dto.contrasena()));
        empleadoRepositorio.save(empleado);
        token = jwtServicio.generateToken(empleado.getUsuario(), "EMPLEADO");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new LoginRespuesta(token, "Bearer", empleado.getUsuario(), "EMPLEADO"));
    }

}
