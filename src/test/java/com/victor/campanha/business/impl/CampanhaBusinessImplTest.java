package com.victor.campanha.business.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.victor.campanha.amqp.AtualizarCampanhaAMQP;
import com.victor.campanha.entity.Campanha;
import com.victor.campanha.generator.CampanhaGenerator;
import com.victor.campanha.repository.CampanhaRepository;
import com.victor.campanha.util.Util;

class CampanhaBusinessImplTest {
	
	@Mock
	private CampanhaRepository campanhaRepository;
	
	@Mock
	private AtualizarCampanhaAMQP atualizarCampanhaAMQP;
	
	@InjectMocks
	private CampanhaBusinessImpl campanhaBusinessImpl;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void when_salvar_should_atualizarCampanhaAMQP() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		this.campanhaBusinessImpl.salvar(campanha);
		
		verify(this.campanhaRepository, times(1)).save(campanha);
		verify(atualizarCampanhaAMQP, times(1)).sendCampanhaAtualizadaMessage(Arrays.asList(campanha));
	}
	
	@Test
	void when_salvar_with_campanhas_should_processarNovoTerminoVigencia() {
		Long terminoVigencia1 = Util.getDateMilliseconds(1);
		Long terminoVigencia2 = Util.getDateMilliseconds(2);
		Long terminoVigencia3 = Util.getDateMilliseconds(3);
		
		Campanha novaCampanha = new CampanhaGenerator().generateValidCampanhaWithRandomId();
		
		Campanha campanhaSalva1 = new CampanhaGenerator().generateValidCampanhaWithRandomId();
		Campanha campanhaSalva2 = new CampanhaGenerator().generateValidCampanhaWithRandomId();
		Campanha campanhaSalva3 = new CampanhaGenerator().generateValidCampanhaWithRandomId();
		
		campanhaSalva1.setTerminoVigencia(terminoVigencia1);
		campanhaSalva2.setTerminoVigencia(terminoVigencia2);
		campanhaSalva3.setTerminoVigencia(terminoVigencia3);
		novaCampanha.setTerminoVigencia(campanhaSalva2.getTerminoVigencia());
		
		List<Campanha> campanhas = new ArrayList<>();
		campanhas.add(campanhaSalva1);
		campanhas.add(campanhaSalva2);
		campanhas.add(campanhaSalva3);
		campanhas.add(novaCampanha);
		
		when(
			this.campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanEqualOrderByTerminoVigenciaAsc(novaCampanha.getTerminoVigencia())
		).thenReturn(
			campanhas
		);
		
		this.campanhaBusinessImpl.salvar(novaCampanha);
		
		verify(this.atualizarCampanhaAMQP, times(2)).sendCampanhaAtualizadaMessage(Mockito.any());
		verify(this.campanhaRepository, times(1)).saveAll(Mockito.any());
		
		Assertions.assertAll(
				() -> Assertions.assertEquals(terminoVigencia1, campanhaSalva1.getTerminoVigencia()),
				() -> Assertions.assertNotEquals(terminoVigencia2, campanhaSalva2.getTerminoVigencia()),
				() -> Assertions.assertNotEquals(terminoVigencia3, campanhaSalva3.getTerminoVigencia())
			);
		
		Assertions.assertAll(
				() -> Assertions.assertNotEquals(campanhaSalva1.getTerminoVigencia(), campanhaSalva2.getTerminoVigencia()),
				() -> Assertions.assertNotEquals(campanhaSalva1.getTerminoVigencia(), campanhaSalva3.getTerminoVigencia()),
				() -> Assertions.assertNotEquals(campanhaSalva1.getTerminoVigencia(), novaCampanha.getTerminoVigencia()),
				() -> Assertions.assertNotEquals(campanhaSalva2.getTerminoVigencia(), campanhaSalva3.getTerminoVigencia()),
				() -> Assertions.assertNotEquals(campanhaSalva2.getTerminoVigencia(), novaCampanha.getTerminoVigencia()),
				() -> Assertions.assertNotEquals(campanhaSalva3.getTerminoVigencia(), novaCampanha.getTerminoVigencia())
			);
	}

}
