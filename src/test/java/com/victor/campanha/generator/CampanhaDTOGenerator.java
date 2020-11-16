package com.victor.campanha.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.victor.campanha.dto.CampanhaDTO;

public class CampanhaDTOGenerator {
	
	public CampanhaDTO generateValidCampanhaDTO() {
		AtomicLong atomicLong = new AtomicLong(100);
		
		CampanhaDTO campanhaDTO = new CampanhaDTO();
		campanhaDTO.setNome("Campanha - "+ atomicLong.getAndIncrement());
		campanhaDTO.setIdTimeCoracao(atomicLong.getAndIncrement());
		campanhaDTO.setInicioVigencia(atomicLong.getAndIncrement());
		campanhaDTO.setTerminoVigencia(atomicLong.getAndIncrement());
		
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
