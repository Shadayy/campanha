package com.victor.campanha.generator;

import com.victor.campanha.entity.Campanha;
import com.victor.campanha.util.Util;

public class CampanhaGenerator {
	
	public Campanha generateValidCampanha() {
		Campanha campanha = new Campanha();
		campanha.setNome("Campanha - "+ AtomicIdGenerator.getInstance().getNewId());
		campanha.setIdTimeCoracao(AtomicIdGenerator.getInstance().getNewId());
		campanha.setInicioVigencia(Util.getDateMilliseconds(1));
		campanha.setTerminoVigencia(Util.getDateMilliseconds(2));
		campanha.setDataCadastro(System.currentTimeMillis());
		campanha.setDeletado(Boolean.FALSE);
		
		return campanha;
	}
	
	public Campanha generateValidCampanhaWithRandomId() {
		Campanha campanha = generateValidCampanha();
		campanha.setId(AtomicIdGenerator.getInstance().getNewId());
		
		return campanha;
	}
}
