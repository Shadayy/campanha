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

import com.victor.campanha.dto.CampanhaDTO;
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
	public ResponseEntity<?> criar(@Valid @RequestBody CampanhaDTO campanha) {
		campanhaService.criar(campanha);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable(required = true) Long id, @Valid @RequestBody CampanhaDTO campanhaDTO) {
		campanhaService.atualizar(id, campanhaDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CampanhaDTO> obter(@PathVariable(required = true) Long id) {
		return new ResponseEntity<CampanhaDTO>(campanhaService.obter(id), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<CampanhaDTO>> listar() {
		return new ResponseEntity<List<CampanhaDTO>>(campanhaService.listarAtivoEVigente(), HttpStatus.OK);
	}
	
	@GetMapping("/listar/{idTimeCoracao}")
	public ResponseEntity<List<CampanhaDTO>> listar(@PathVariable(required = true) Long idTimeCoracao) {
		return new ResponseEntity<List<CampanhaDTO>>(campanhaService.listarAtivoEVigente(idTimeCoracao), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletar(@PathVariable(required = true) Long id) {
		campanhaService.deletar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
