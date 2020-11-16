package com.victor.campanha.integration.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.victor.campanha.entity.Campanha;
import com.victor.campanha.repository.CampanhaRepository;


@DataJpaTest
class CampanhaRepositoryTest {
	
	@Autowired
	CampanhaRepository campanhaRepository;
	
	private static AtomicLong atomicLong;
	
	@BeforeAll
	static void setup() {
		atomicLong = new AtomicLong();
		atomicLong.getAndIncrement();
	}
	
	@Test
	void when_save_campanha_should_findByIdAndDeletadoFalse() {
		Campanha campanha = new Campanha();
		campanha.setNome("Campanha");
		campanha.setInicioVigencia(atomicLong.getAndIncrement());
		campanha.setTerminoVigencia(atomicLong.getAndIncrement());
		campanha.setIdTimeCoracao(atomicLong.getAndIncrement());
		campanha.setDeletado(false);
		
		campanhaRepository.save(campanha);
		
		Optional<Campanha> campanhaOptional = campanhaRepository.findByIdAndDeletadoFalse(campanha.getId());
		
		Assertions.assertTrue(campanhaOptional.isPresent() && campanhaOptional.get().equals(campanha));
	}

	@Test
	void when_save_campanha_should_findAllByDeletadoFalseAndTerminoVigenciaLessThan() {
		long terminoVigencia = atomicLong.getAndIncrement();
		
		Campanha campanha = new Campanha();
		campanha.setNome("Campanha");
		campanha.setInicioVigencia(atomicLong.getAndIncrement());
		campanha.setTerminoVigencia(terminoVigencia);
		campanha.setIdTimeCoracao(atomicLong.getAndIncrement());
		campanha.setDeletado(false);
		
		campanhaRepository.save(campanha);
		
		List<Campanha> campanhas = campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThan(terminoVigencia -1);
		
		Assertions.assertTrue(() -> campanhas.contains(campanha));
	}
	
	@Test
	void when_save_campanha_should_findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracaoEqual() {
		long terminoVigencia = atomicLong.getAndIncrement();
		
		Campanha campanha = new Campanha();
		campanha.setNome("Campanha");
		campanha.setInicioVigencia(atomicLong.getAndIncrement());
		campanha.setTerminoVigencia(terminoVigencia);
		campanha.setIdTimeCoracao(atomicLong.getAndIncrement());
		campanha.setDeletado(false);
		
		campanhaRepository.save(campanha);
		
		List<Campanha> campanhas = campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracao(campanha.getTerminoVigencia() -1, campanha.getIdTimeCoracao());
		
		Assertions.assertTrue(() -> campanhas.contains(campanha));
	}

}
