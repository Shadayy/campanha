package com.victor.campanha.amqp.impl;

import java.util.List;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.campanha.amqp.AtualizarCampanhaAMQP;
import com.victor.campanha.entity.Campanha;

@Component
public class AtualizarCampanhaAMQPImpl implements AtualizarCampanhaAMQP {
	
	private RabbitTemplate rabbitTemplate;
	private ObjectMapper objectMapper;
	
	@Autowired
	public AtualizarCampanhaAMQPImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}
	
	@Bean
	@Override
	public Queue campanhasAtualizadasQueue() {
		return new Queue("campanha-atualizada", false);
	}
	
	@Override
	public void sendCampanhaAtualizadaMessage(List<Campanha> campanhas) {
		// TODO NAO TESTADO
		
		campanhas.forEach(campanha -> {
			try {
				this.rabbitTemplate.convertAndSend("campanha-atualizada", objectMapper.writeValueAsString(campanha));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
}
