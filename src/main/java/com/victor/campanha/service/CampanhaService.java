package com.victor.campanha.service;

import java.util.List;

import com.victor.campanha.dto.CampanhaDTOReceive;
import com.victor.campanha.dto.CampanhaDTOResponse;

public interface CampanhaService {

	public void criar(CampanhaDTOReceive campanha);
	
	public void atualizar(Long id, CampanhaDTOReceive campanhaDTO);
	
	public CampanhaDTOResponse obter(Long id);
	
	public List<CampanhaDTOResponse> listarAtivoEVigente();
	
	public void deletar(Long id);

	public List<CampanhaDTOResponse> listarAtivoEVigente(Long idTimeCoracao);
}
