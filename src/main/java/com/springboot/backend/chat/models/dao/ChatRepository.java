package com.springboot.backend.chat.models.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springboot.backend.chat.models.documents.Mensaje;

public interface ChatRepository extends MongoRepository<Mensaje, String> {

	//Devolver los ultimos 10 mensajes, ordenados por fecha descendentemente
	public List<Mensaje> findFirst10ByOrderByFechaDesc();
}
