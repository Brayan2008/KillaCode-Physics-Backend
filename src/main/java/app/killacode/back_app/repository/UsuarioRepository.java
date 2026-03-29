package app.killacode.back_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.killacode.back_app.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByCorreo(String correo);
}