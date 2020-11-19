package com.victor.campanha.amqp.impl;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.campanha.entity.Campanha;
import com.victor.campanha.exception.InaccessibleQueueException;
import com.victor.campanha.generator.CampanhaGenerator;

class AtualizarCampanhaAMQPImplTest {
	
	@Mock
	private RabbitTemplate rabbitTemplate;
	
	@Mock
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private AtualizarCampanhaAMQPImpl atualizarCampanhaAMQPImpl;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void when_sendCampanhaAtualizadaMessage_fail_should_throw_InaccessibleQueueException() throws Exception {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		
		when(objectMapper.writeValueAsString(campanha)).thenReturn("{}");
		doThrow(AmqpException.class).when(this.rabbitTemplate).convertAndSend(Mockito.anyString(), Mockito.anyString());
		
		Assertions.assertThrows(
				InaccessibleQueueException.class, 
				() -> this.atualizarCampanhaAMQPImpl.sendCampanhaAtualizadaMessage(Collections.singletonList(campanha))
		);
	}
	
	@Test
	void when_sendCampanhaAtualizadaMessage_with_valid_data_should_deserialize() throws Exception {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		List<Campanha> campanhas = Collections.singletonList(campanha);
		
		this.atualizarCampanhaAMQPImpl.sendCampanhaAtualizadaMessage(campanhas);
		
		verify(this.objectMapper, times(1)).writeValueAsString(campanha);
	}
	
	@Test
	void when_sendCampanhaAtualizadaMessage_should_convertAndSend() throws Exception {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		List<Campanha> campanhas = Collections.singletonList(campanha);
		
		when(objectMapper.writeValueAsString(campanha)).thenReturn("{}");
		this.atualizarCampanhaAMQPImpl.sendCampanhaAtualizadaMessage(campanhas);
		
		verify(rabbitTemplate, times(1)).convertAndSend(Mockito.anyString(), Mockito.anyString());
	}

}
