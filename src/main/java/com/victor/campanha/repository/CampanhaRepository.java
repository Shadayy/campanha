package com.victor.campanha.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.victor.campanha.entity.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Long>, JpaSpecificationExecutor<Campanha>{
	
	Optional<Campanha> findByIdAndDeletadoFalse(Long id);
	
	List<Campanha> findAllByDeletadoFalseAndTerminoVigenciaGreaterThan(Long terminoVigencia);
	
	List<Campanha> findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracao(Long terminoVigencia, Long idTimeCoracao);
}
