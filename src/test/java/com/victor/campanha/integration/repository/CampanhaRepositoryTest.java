package com.victor.campanha.integration.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.victor.campanha.entity.Campanha;
import com.victor.campanha.generator.CampanhaGenerator;
import com.victor.campanha.repository.CampanhaRepository;


@DataJpaTest
class CampanhaRepositoryTest {
	
	@Autowired
	CampanhaRepository campanhaRepository;
	
	@Test
	void when_save_campanha_should_findByIdAndDeletadoFalse() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		campanhaRepository.save(campanha);
		
		Optional<Campanha> campanhaOptional = campanhaRepository.findByIdAndDeletadoFalse(campanha.getId());
		
		Assertions.assertTrue(campanhaOptional.isPresent() && campanhaOptional.get().equals(campanha));
	}

	@Test
	void when_save_campanha_should_findAllByDeletadoFalseAndTerminoVigenciaLessThan() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		campanhaRepository.save(campanha);
		
		List<Campanha> campanhas = campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanEqualOrderByTerminoVigenciaAsc(campanha.getTerminoVigencia() -1);
		
		Assertions.assertTrue(() -> campanhas.contains(campanha));
	}
	
	@Test
	void when_save_campanha_should_findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracaoEqual() {
		Campanha campanha = new CampanhaGenerator().generateValidCampanha();
		campanhaRepository.save(campanha);
		
		List<Campanha> campanhas = campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracao(campanha.getTerminoVigencia() -1, campanha.getIdTimeCoracao());
		
		Assertions.assertTrue(() -> campanhas.contains(campanha));
	}

}
