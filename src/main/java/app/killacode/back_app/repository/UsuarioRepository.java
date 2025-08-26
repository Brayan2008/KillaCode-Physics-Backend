package app.killacode.back_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.killacode.back_app.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{}