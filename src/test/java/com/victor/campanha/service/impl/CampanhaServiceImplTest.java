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

import com.victor.campanha.dto.CampanhaDTO;
import com.victor.campanha.entity.Campanha;
import com.victor.campanha.generator.CampanhaGenerator;
import com.victor.campanha.repository.CampanhaRepository;

class CampanhaServiceImplTest {
	
	@Mock
	private CampanhaRepository campanhaRepository;
	
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
		
		when(campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThan(Mockito.anyLong())).thenReturn(Collections.singletonList(campanha));
		List<CampanhaDTO> campanhasDTO = campanhaServiceImpl.listarAtivoEVigente();
		CampanhaDTO campanhaDTO = campanhasDTO.get(0);
		
		Assertions.assertAll(
				() -> Assertions.assertEquals(campanha.getNome(), campanhaDTO.getNome()),
				() -> Assertions.assertEquals(campanha.getIdTimeCoracao(), campanhaDTO.getIdTimeCoracao()),
				() -> Assertions.assertEquals(campanha.getInicioVigencia(), campanhaDTO.getInicioVigencia()),
				() -> Assertions.assertEquals(campanha.getTerminoVigencia(), campanhaDTO.getTerminoVigencia())
		);
		
		when(campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThan(Mockito.anyLong())).thenReturn(Collections.emptyList());
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
		Campanha mockCampanha = Mockito.mock(Campanha.class);
		
		when(campanhaRepository.findByIdAndDeletadoFalse(mockCampanha.getId())).thenReturn(Optional.of(mockCampanha));
		campanhaServiceImpl.deletar(mockCampanha.getId());
		
		verify(mockCampanha, times(1)).deletar();
	}
}
