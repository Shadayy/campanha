package com.victor.campanha.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.victor.campanha.dto.CampanhaDTO;
import com.victor.campanha.entity.Campanha;
import com.victor.campanha.repository.CampanhaRepository;
import com.victor.campanha.service.CampanhaService;

@Service
@Transactional
public class CampanhaServiceImpl implements CampanhaService {
	
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	public CampanhaServiceImpl(CampanhaRepository campanhaRepository) {
		this.campanhaRepository = campanhaRepository;
	}
	
	@Override
	public Long criar(CampanhaDTO campanhaDTO) {
		Campanha campanhaSalva = this.campanhaRepository.save(new Campanha(campanhaDTO));
		return campanhaSalva.getId();
		
		//TODO atualziar datas
		//TODO disparar evento notificacao
	}

	@Override
	public void atualizar(Long id, CampanhaDTO campanhaDTO) {
		getCampanhaOrThrow(id);
		
		Campanha campanha = new Campanha(campanhaDTO);
		campanha.setId(id);
		this.campanhaRepository.save(campanha);
		
		//TODO atualziar datas
		//TODO disparar evento notificacao
	}

	@Override
	public CampanhaDTO obter(Long id) {
		Campanha campanha = getCampanhaOrThrow(id);
		return new CampanhaDTO(campanha);
	}

	@Override
	public List<CampanhaDTO> listarAtivoEVigente() {
		List<Campanha> campanhas = this.campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThan(System.currentTimeMillis());
		
		return campanhas.stream()
				.map(campanha ->
					new CampanhaDTO(campanha)
				)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<CampanhaDTO> listarAtivoEVigente(Long idTimeDoCoracao) {
		List<Campanha> campanhas = this.campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanAndIdTimeCoracao(System.currentTimeMillis(), idTimeDoCoracao);
		
		return campanhas.stream()
				.map(campanha ->
					new CampanhaDTO(campanha)
				)
				.collect(Collectors.toList());
	}

	@Override
	public void deletar(Long id) {
		Campanha campanha = getCampanhaOrThrow(id);
		this.campanhaRepository.save(campanha.deletar());
	}
	
	private Campanha getCampanhaOrThrow(Long id) {
		Optional<Campanha> optionalCampanha = this.campanhaRepository.findByIdAndDeletadoFalse(id);
		
		if(!optionalCampanha.isPresent()) {
			throw new EntityNotFoundException();
		}
		
		return optionalCampanha.get();
	}
}
