package com.tuempresa.backapp.service;

import java.util.List;

import com.tuempresa.backapp.model.MalasRespuesta;

public interface MalasRespuestasService {
	MalasRespuesta findById(Long id);
	MalasRespuesta create(MalasRespuesta entity);
	MalasRespuesta update(Long id, MalasRespuesta entity);
	void delete(Long id);
}