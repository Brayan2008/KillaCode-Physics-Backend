package com.tuempresa.backapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.tuempresa.backapp.model.MalasRespuesta;
import com.tuempresa.backapp.repository.MalasRespuestasRepository;
import com.tuempresa.backapp.service.MalasRespuestasService;

@Service
@Transactional
public class MalasRespuestasServiceImpl implements MalasRespuestasService {
	
    @Autowired
    private MalasRespuestasRepository repo;

	@Override
	public MalasRespuesta findById(Long id) { 
		Optional<MalasRespuesta> opt = repo.findById(id);
		return opt.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MalasRespuesta no encontrada: " + id));
	}

	@Override
	public MalasRespuesta create(MalasRespuesta entity) {
		entity.setId(null); // asegurarse de que sea nuevo
		return repo.save(entity);
	}

	@Override
	public MalasRespuesta update(Long id, MalasRespuesta entity) {
		MalasRespuesta existing = findById(id);
		// Actualizar campos editables — ajustar según la entidad real
		existing.setTexto(entity.getTexto());
		existing.setActiva(entity.isActiva());
		return repo.save(existing);
	}

	@Override
	public void delete(Long id) {
		MalasRespuesta existing = findById(id);
		repo.delete(existing);
	}
}