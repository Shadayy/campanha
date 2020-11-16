package com.victor.campanha.service;

import java.util.List;

import com.victor.campanha.dto.CampanhaDTO;

public interface CampanhaService {

	public void criar(CampanhaDTO campanha);
	
	public void atualizar(Long id, CampanhaDTO campanhaDTO);
	
	public CampanhaDTO obter(Long id);
	
	public List<CampanhaDTO> listarAtivoEVigente();
	
	public void deletar(Long id);

	public List<CampanhaDTO> listarAtivoEVigente(Long idTimeCoracao);
}
