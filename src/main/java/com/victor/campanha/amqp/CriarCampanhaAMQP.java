package com.victor.campanha.amqp;

import java.io.IOException;

import org.springframework.amqp.core.Queue;

import com.victor.campanha.entity.Campanha;

public interface CriarCampanhaAMQP {
	
	public Queue criarCampanhaQueue();
	
	public void sendCriarCampanhaMessage(Campanha campanha);
	
	public void receiveCriarCampanhaMessage(String content) throws IOException;
}
