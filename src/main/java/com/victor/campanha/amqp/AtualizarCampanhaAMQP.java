package com.victor.campanha.amqp;

import java.util.List;

import org.springframework.amqp.core.Queue;

import com.victor.campanha.entity.Campanha;

public interface AtualizarCampanhaAMQP {
	
	public Queue campanhasAtualizadasQueue();
	
	public void sendCampanhaAtualizadaMessage(List<Campanha> campanhasAtualizas);
}
