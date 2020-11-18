package com.victor.campanha.amqp.impl;

import java.io.IOException;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.campanha.amqp.CriarCampanhaAMQP;
import com.victor.campanha.business.CampanhaBusiness;
import com.victor.campanha.entity.Campanha;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CriarCampanhaAMQPImpl implements CriarCampanhaAMQP{
	
	private RabbitTemplate rabbitTemplate;
	private ObjectMapper objectMapper;
	private CampanhaBusiness campanhaBusiness;
	
	@Autowired
	public CriarCampanhaAMQPImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, CampanhaBusiness campanhaBusiness) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
		this.campanhaBusiness = campanhaBusiness;
	}
	
	@Bean
	@Override
	public Queue criarCampanhaQueue() {
		return new Queue("criar-campanha", false);
	}
	
	@Override
	public void sendCriarCampanhaMessage(Campanha campanha) {
		try {
			this.rabbitTemplate.convertAndSend("criar-campanha", objectMapper.writeValueAsString(campanha));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@RabbitListener(queues = "criar-campanha", concurrency = "1", exclusive = true)
	@Override
	public void receiveCriarCampanhaMessage(String content) throws IOException {
		log.debug("@@@@@@@@@@@@ msg criar-campanha: {}", content);
		
		Campanha campanha = objectMapper.readValue (content, Campanha.class);
		
		this.campanhaBusiness.salvar(campanha);
	}

}
