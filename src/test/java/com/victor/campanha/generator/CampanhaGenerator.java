package com.victor.campanha.generator;

import com.victor.campanha.entity.Campanha;

public class CampanhaGenerator {
	
	public Campanha generateValidCampanha() {
		Campanha campanha = new Campanha();
		campanha.setNome("Campanha - "+ AtomicIdGenerator.getInstance().getNewId());
		campanha.setIdTimeCoracao(AtomicIdGenerator.getInstance().getNewId());
		campanha.setInicioVigencia(AtomicIdGenerator.getInstance().getNewId());
		campanha.setTerminoVigencia(AtomicIdGenerator.getInstance().getNewId());
		campanha.setDataCadastro(System.currentTimeMillis());
		campanha.setDeletado(Boolean.FALSE);
		campanha.setId(AtomicIdGenerator.getInstance().getNewId());
		
		return campanha;
	}
}
