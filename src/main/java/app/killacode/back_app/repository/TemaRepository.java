package app.killacode.back_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.killacode.back_app.model.Tema;

public interface TemaRepository extends JpaRepository<Tema, String>{}