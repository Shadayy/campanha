package com.victor.campanha.generator;

import java.util.concurrent.atomic.AtomicLong;

import com.victor.campanha.entity.Campanha;

public class CampanhaGenerator {
	
	public Campanha generateValidCampanha() {
		AtomicLong atomicLong = new AtomicLong(100);
		
		Campanha campanha = new Campanha();
		campanha.setNome("Campanha - "+ atomicLong.getAndIncrement());
		campanha.setIdTimeCoracao(atomicLong.getAndIncrement());
		campanha.setInicioVigencia(atomicLong.getAndIncrement());
		campanha.setTerminoVigencia(atomicLong.getAndIncrement());
		campanha.setDataCadastro(System.currentTimeMillis());
		campanha.setDeletado(Boolean.FALSE);
		campanha.setId(atomicLong.getAndIncrement());
		
		return campanha;
	}
}
