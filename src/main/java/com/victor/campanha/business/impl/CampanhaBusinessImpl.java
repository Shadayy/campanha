package com.victor.campanha.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victor.campanha.amqp.AtualizarCampanhaAMQP;
import com.victor.campanha.business.CampanhaBusiness;
import com.victor.campanha.entity.Campanha;
import com.victor.campanha.repository.CampanhaRepository;

@Component
public class CampanhaBusinessImpl implements CampanhaBusiness{
	
	private CampanhaRepository campanhaRepository;
	private AtualizarCampanhaAMQP atualizarCampanhaAMQP;
	
	@Autowired
	public CampanhaBusinessImpl(CampanhaRepository campanhaRepository, AtualizarCampanhaAMQP atualizarCampanhaAMQP) {
		this.campanhaRepository = campanhaRepository;
		this.atualizarCampanhaAMQP = atualizarCampanhaAMQP;
	}
	
	@Override
	public void salvar(Campanha campanha) {
		this.campanhaRepository.save(campanha);
		List<Campanha> campanhas = this.campanhaRepository.findAllByDeletadoFalseAndTerminoVigenciaGreaterThanEqualOrderByTerminoVigenciaAsc(campanha.getTerminoVigencia());
		
		if(!campanhas.isEmpty()) {
			processarNovoTerminoVigencia(campanha.getId(), campanhas);
		}
		
		this.atualizarCampanhaAMQP.sendCampanhaAtualizadaMessage(Collections.singletonList(campanha));
	}
	
	private void processarNovoTerminoVigencia(Long idCampanhaIgnorar, List<Campanha> campanhasAtualizar) {
		List<Campanha> campanhasAtualizadas = new ArrayList<Campanha>();
		
		for (Campanha campanhaA : campanhasAtualizar) {
			if(campanhaA.getId().equals(idCampanhaIgnorar)) continue;
			
			for (Campanha campanhaB : campanhasAtualizar) {
				if(!campanhaA.getId().equals(campanhaB.getId()) && campanhaA.getTerminoVigencia().equals(campanhaB.getTerminoVigencia())) {
					campanhaA.aumentarDiasTerminoVigencia();
					campanhasAtualizadas.add(campanhaA);
					break;
				}
			}
		}
		
		if(!campanhasAtualizadas.isEmpty()) {
			this.campanhaRepository.saveAll(campanhasAtualizadas);
			this.atualizarCampanhaAMQP.sendCampanhaAtualizadaMessage(campanhasAtualizadas);
		}
	}
}
