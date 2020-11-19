package com.victor.campanha.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.victor.campanha.entity.Campanha;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampanhaDTOResponse extends CampanhaDTOReceive {
	
	private Long id;
	
	public String getInicioVigenciaFormatado() {
		return getDataFormatada(super.getInicioVigencia());
	}
	
	public String getTerminoVigenciaFormatado() {
		return getDataFormatada(super.getTerminoVigencia());
	}
	
	private String getDataFormatada(Long data) {
		if(data == null) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date(data);
		
		return sdf.format(date);
	}
	public CampanhaDTOResponse() {
		
	}
	
	public CampanhaDTOResponse(Campanha campanha){
		super(campanha);
		setId(campanha.getId());
	}
}
