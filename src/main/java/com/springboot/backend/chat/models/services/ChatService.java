package com.springboot.backend.chat.models.services;

import java.util.List;

import com.springboot.backend.chat.models.documents.Mensaje;

public interface ChatService {
	
	public List<Mensaje> obtenerUltimos10Mensajes();
	
	public Mensaje guardar(Mensaje mensaje);
}
