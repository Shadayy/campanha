package com.victor.campanha.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.victor.campanha.amqp.CriarCampanhaAMQP;
import com.victor.campanha.dto.CampanhaDTO;
import com.victor.campanha.entity.Campanha;
import com.victor.campanha.generator.CampanhaDTOGenerator;
import com.victor.campanha.generator.CampanhaGenerator;
import com.victor.campanha.repository.CampanhaRepository;

class CampanhaServiceImplTest {
	
	@Mock
	private CampanhaRepository campanhaRepository;
	
	@Mock
	private CriarCampanhaAMQP criarCampanhaAMQP;
	
	@InjectMocks
	private CampanhaServiceImpl campanhaServiceImpl;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void when_obter_with_valid_id_should_return() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		
		when(campanhaRepository.findByIdAndDeletadoFalse(campanha.getId())).thenReturn(Optional.of(campanha));
		CampanhaDTO campanhaDTO = campanhaServiceImpl.obter(campanha.getId());
		
		Assertions.assertAll(
				() -> Assertions.assertEquals(campanha.getNome(), campanhaDTO.getNome()),
				() -> Assertions.assertEquals(campanha.getIdTimeCoracao(), campanhaDTO.getIdTimeCoracao()),
				() -> Assertions.assertEquals(campanha.getInicioVigencia(), campanhaDTO.getInicioVigencia()),
				() -> Assertions.assertEquals(campanha.getTerminoVigencia(), campanhaDTO.getTerminoVigencia())
		);
	}
	
	@Test
	void when_obter_with_invalid_id_should_throw() {
		Long idCampanha = 1l;
		when(campanhaRepository.findByIdAndDeletadoFalse(idCampanha)).thenReturn(Optional.empty());
		
		Assertions.assertThrows(
				EntityNotFoundException.class, 
				() -> campanhaServiceImpl.obter(idCampanha)
		);
	}
	
	@Test
	void when_listarAtivoEVigente_should_return_list() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		
		when(campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanEqualOrderByTerminoVigenciaAsc(Mockito.anyLong())).thenReturn(Collections.singletonList(campanha));
		List<CampanhaDTO> campanhasDTO = campanhaServiceImpl.listarAtivoEVigente();
		CampanhaDTO campanhaDTO = campanhasDTO.get(0);
		
		Assertions.assertAll(
				() -> Assertions.assertEquals(campanha.getNome(), campanhaDTO.getNome()),
				() -> Assertions.assertEquals(campanha.getIdTimeCoracao(), campanhaDTO.getIdTimeCoracao()),
				() -> Assertions.assertEquals(campanha.getInicioVigencia(), campanhaDTO.getInicioVigencia()),
				() -> Assertions.assertEquals(campanha.getTerminoVigencia(), campanhaDTO.getTerminoVigencia())
		);
		
		when(campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanEqualOrderByTerminoVigenciaAsc(Mockito.anyLong())).thenReturn(Collections.emptyList());
		campanhasDTO = campanhaServiceImpl.listarAtivoEVigente();
		
		Assertions.assertTrue(campanhasDTO.isEmpty());
	}
	
	@Test
	void when_listarAtivoEVigente_with_id_should_return_list() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		
		when(campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracao(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Collections.singletonList(campanha));
		List<CampanhaDTO> campanhasDTO = campanhaServiceImpl.listarAtivoEVigente(campanha.getIdTimeCoracao());
		CampanhaDTO campanhaDTO = campanhasDTO.get(0);
		
		Assertions.assertAll(
				() -> Assertions.assertEquals(campanha.getNome(), campanhaDTO.getNome()),
				() -> Assertions.assertEquals(campanha.getIdTimeCoracao(), campanhaDTO.getIdTimeCoracao()),
				() -> Assertions.assertEquals(campanha.getInicioVigencia(), campanhaDTO.getInicioVigencia()),
				() -> Assertions.assertEquals(campanha.getTerminoVigencia(), campanhaDTO.getTerminoVigencia())
		);
		
		when(campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracao(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Collections.emptyList());
		campanhasDTO = campanhaServiceImpl.listarAtivoEVigente(campanha.getIdTimeCoracao());
		
		Assertions.assertTrue(campanhasDTO.isEmpty());
	}
	
	@Test
	void when_deletar_with_invalid_id_should_throw() {
		Long idCampanha = 1l;
		when(campanhaRepository.findByIdAndDeletadoFalse(idCampanha)).thenReturn(Optional.empty());
		
		Assertions.assertThrows(
				EntityNotFoundException.class, 
				() -> campanhaServiceImpl.deletar(idCampanha)
		);
	}
	
	@Test
	void when_deletar_with_valid_id_should_deletar() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		
		when(campanhaRepository.findByIdAndDeletadoFalse(campanha.getId())).thenReturn(Optional.of(campanha));
		campanhaServiceImpl.deletar(campanha.getId());
		
		Assertions.assertTrue(campanha.isDeletado());
	}
	
	@Test
	void when_atualizar_with_invalid_id_should_throw() {
		Long idCampanha = 1l;
		CampanhaDTO campanhaDTO = new CampanhaDTOGenerator().generateValidCampanhaDTO();
		when(campanhaRepository.findByIdAndDeletadoFalse(idCampanha)).thenReturn(Optional.empty());
		
		Assertions.assertThrows(
				EntityNotFoundException.class, 
				() -> campanhaServiceImpl.atualizar(idCampanha, campanhaDTO)
		);
	}
	
	@Test
	void when_atualizar_with_valid_id_should_atualizarr() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		CampanhaDTO campanhaDTO = new CampanhaDTOGenerator().generateValidCampanhaDTO();
		
		Assertions.assertAll(
				() -> Assertions.assertNotEquals(campanha.getNome(), campanhaDTO.getNome()),
				() -> Assertions.assertNotEquals(campanha.getIdTimeCoracao(), campanhaDTO.getIdTimeCoracao()),
				() -> Assertions.assertNotEquals(campanha.getInicioVigencia(), campanhaDTO.getInicioVigencia()),
				() -> Assertions.assertNotEquals(campanha.getTerminoVigencia(), campanhaDTO.getTerminoVigencia())
		);
		
		when(campanhaRepository.findByIdAndDeletadoFalse(campanha.getId())).thenReturn(Optional.of(campanha));
		
		campanhaServiceImpl.atualizar(campanha.getId(), campanhaDTO);
		
		Assertions.assertAll(
				() -> Assertions.assertEquals(campanha.getNome(), campanhaDTO.getNome()),
				() -> Assertions.assertEquals(campanha.getIdTimeCoracao(), campanhaDTO.getIdTimeCoracao()),
				() -> Assertions.assertEquals(campanha.getInicioVigencia(), campanhaDTO.getInicioVigencia()),
				() -> Assertions.assertEquals(campanha.getTerminoVigencia(), campanhaDTO.getTerminoVigencia())
		);
		
		verify(criarCampanhaAMQP, times(1)).sendCriarCampanhaMessage(Mockito.any());
	}
	
	@Test
	void when_criar_should_call_sendCriarCampanhaMessage() {
		CampanhaDTO campanhaDTO = new CampanhaDTOGenerator().generateValidCampanhaDTO();
		
		campanhaServiceImpl.criar(campanhaDTO);
		
		verify(criarCampanhaAMQP, times(1)).sendCriarCampanhaMessage(Mockito.any());
	}
}
