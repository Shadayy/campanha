package com.victor.campanha.generator;

import java.util.ArrayList;
import java.util.List;

import com.victor.campanha.dto.CampanhaDTO;

public class CampanhaDTOGenerator {
	
	public CampanhaDTO generateValidCampanhaDTO() {
		CampanhaDTO campanhaDTO = new CampanhaDTO();
		campanhaDTO.setNome("Campanha - "+ AtomicIdGenerator.getInstance().getNewId());
		campanhaDTO.setIdTimeCoracao(AtomicIdGenerator.getInstance().getNewId());
		campanhaDTO.setInicioVigencia(AtomicIdGenerator.getInstance().getNewId());
		campanhaDTO.setTerminoVigencia(AtomicIdGenerator.getInstance().getNewId());
		
		return campanhaDTO;
	}
	
	public List<CampanhaDTO> getInvalidListCampanhaDTO(){
		List<CampanhaDTO> campanhasDTO = new ArrayList<>();
		
		CampanhaDTO campanhaDTO = generateValidCampanhaDTO();
		campanhaDTO.setIdTimeCoracao(null);
		campanhasDTO.add(campanhaDTO);
		
		
		campanhaDTO = generateValidCampanhaDTO();
		campanhaDTO.setInicioVigencia(null);
		campanhasDTO.add(campanhaDTO);
		
		campanhaDTO = generateValidCampanhaDTO();
		campanhaDTO.setNome(null);
		campanhasDTO.add(campanhaDTO);
		
		campanhaDTO = generateValidCampanhaDTO();
		campanhaDTO.setTerminoVigencia(null);
		campanhasDTO.add(campanhaDTO);
		
		return campanhasDTO;
	}
}
