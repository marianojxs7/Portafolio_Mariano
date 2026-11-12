
package com.Portafolio.service;

import com.Portafolio.domain.Usuario;
import com.Portafolio.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final HttpSession session;

    public UsuarioDetailsService(
            UsuarioRepository usuarioRepository,
            HttpSession session) {

        this.usuarioRepository = usuarioRepository;
        this.session = session;
    }

    // Este método busca el registro con el username pasado
    // del login, en la tabla usuario.
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // Se busca el usuario por username y activo
        Usuario usuario = usuarioRepository
                .findByUsernameAndActivoTrue(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                        "Usuario no encontrado: " + username
                    )
                );

        // Se guarda la foto del usuario en la sesión
        session.removeAttribute("imagenUsuario");
        session.setAttribute(
                "imagenUsuario",
                usuario.getRutaImagen()
        );

        // Se cargan los roles del usuario
        var roles = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(
                    "ROLE_" + rol.getRol()
                ))
                .collect(Collectors.toSet());

        // Se retorna el usuario con su información
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                roles
        );
    }
}
