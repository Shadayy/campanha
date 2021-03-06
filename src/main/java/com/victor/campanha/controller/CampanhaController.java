package com.victor.campanha.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victor.campanha.dto.CampanhaDTOReceive;
import com.victor.campanha.dto.CampanhaDTOResponse;
import com.victor.campanha.service.CampanhaService;

@RestController
@RequestMapping("/campanha")
public class CampanhaController {
	
	private CampanhaService campanhaService;
	
	@Autowired
	public CampanhaController(CampanhaService campanhaService) {
		this.campanhaService = campanhaService;
	}
	
	@PostMapping
	public ResponseEntity<Void> criar(@Valid @RequestBody CampanhaDTOReceive campanha) {
		campanhaService.criar(campanha);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@PathVariable(required = true) Long id, @Valid @RequestBody CampanhaDTOReceive campanhaDTO) {
		campanhaService.atualizar(id, campanhaDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CampanhaDTOResponse> obter(@PathVariable(required = true) Long id) {
		return new ResponseEntity<>(campanhaService.obter(id), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<CampanhaDTOResponse>> listar() {
		return new ResponseEntity<>(campanhaService.listarAtivoEVigente(), HttpStatus.OK);
	}
	
	@GetMapping("/listar/{idTimeCoracao}")
	public ResponseEntity<List<CampanhaDTOResponse>> listar(@PathVariable(required = true) Long idTimeCoracao) {
		return new ResponseEntity<>(campanhaService.listarAtivoEVigente(idTimeCoracao), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable(required = true) Long id) {
		campanhaService.deletar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
