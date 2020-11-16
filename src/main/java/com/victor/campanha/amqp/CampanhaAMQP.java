package com.victor.campanha.amqp;

import org.springframework.amqp.core.Queue;

import com.victor.campanha.entity.Campanha;

public interface CampanhaAMQP {
	
	public Queue criarCampanhaQueue();
	
	public Queue campanhasAtualizadasQueue();
	
	public void sendCriarCampanhaMessage(Campanha campanha);
	
	public void sendCampanhaAtualizadaMessage(Campanha campanha);
	
	public void receiveCriarCampanhaMessage(String content);
}
