package com.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.springboot.backend.chat.models.documents.Mensaje;
import com.springboot.backend.chat.models.services.ChatService;

@Controller
public class ChatController {
	
	private String[] colores = {"red", "green", "blue", "magenta", "purple", "orange", "yellow"};
	
	@Autowired
	SimpMessagingTemplate websocket;
	
	@Autowired
	private ChatService chatService;
	
	@MessageMapping("/mensaje")
	@SendTo("/chat/mensaje") //Envia un evento a todos los suscritos
	public Mensaje recibeMensaje(Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		
		if(mensaje.getTipo().equals("NUEVO_USUARIO")){
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("nuevo usuario");
		} else {
			//Guardamos solo los mensajes.
			chatService.guardar(mensaje);
		}
		
		return mensaje;
	}
	
	@MessageMapping("/escribiendo") //Avisa que alguien esta escribiendo
	@SendTo("/chat/escribiendo") //Notificar cuando alguien esta escribiendo
	public String estaEscribiendo(String username) {
		return username.concat(" está escribiendo ...");
	}
	
	@MessageMapping("/historial")
	//En este caso no se puede usar el SendTo porque debe devolverse solo al cliente que se conecto,
	// y no notificar a todos, que ya obtuvieron los msjs al conectarse.
	public void historial(String clienteId) {
		//Para esto usamos el SimpMessagingTemplate, pasando un id de cliente.
		websocket.convertAndSend("/chat/historial/" + clienteId, chatService.obtenerUltimos10Mensajes());
	}

}
