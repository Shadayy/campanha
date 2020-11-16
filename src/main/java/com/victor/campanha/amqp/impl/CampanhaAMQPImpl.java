package com.victor.campanha.amqp.impl;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.campanha.amqp.CampanhaAMQP;
import com.victor.campanha.entity.Campanha;

@Component
public class CampanhaAMQPImpl implements CampanhaAMQP{
	
	private RabbitTemplate rabbitTemplate;
	private ObjectMapper objectMapper;
	
	
	@Autowired
	public CampanhaAMQPImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}
	
	@Bean
	@Override
	public Queue criarCampanhaQueue() {
		return new Queue("criar-campanha", false);
	}
	
	@Bean
	@Override
	public Queue campanhasAtualizadasQueue() {
		return new Queue("campanha-atualizada", false);
	}
	
	@Override
	public void sendCriarCampanhaMessage(Campanha campanha) {
		try {
			this.rabbitTemplate.convertAndSend("criar-campanha", objectMapper.writeValueAsString(campanha));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void sendCampanhaAtualizadaMessage(Campanha campanha) {
		try {
			this.rabbitTemplate.convertAndSend("campanha-atualizada", objectMapper.writeValueAsString(campanha));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@RabbitListener(queues = "criar-campanha", concurrency = "1", exclusive = true)
	@Override
	public void receiveCriarCampanhaMessage(String content) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println(content);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		
		Campanha campanha = objectMapper.convertValue(content, Campanha.class);
		//TODO salvar campanha
		//TODO atualziar datas
		//TODO disparar evento notificacao
	}

}
