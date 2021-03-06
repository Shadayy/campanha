package com.victor.campanha.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.victor.campanha.dto.CampanhaDTOReceive;
import com.victor.campanha.util.Util;

import lombok.Data;

@Data
@Entity
public class Campanha {
	
	private static final Long DIA_MILLISEGUNDO = 86400000L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome da campanha é obrigatório")
	@Size(max = 255, message = "O nome da campanha é limitado em 255 caracteres")
	private String nome;
	
	@NotNull(message = "ID do Time do Coração é obrigatório")
	@Column(name= "IDTIMEDOCORACAO")
	private Long idTimeCoracao;
	
	@Min(value = 1, message="Inicio da vigência deve ser maior que 0")
	@NotNull(message = "Inicio da vigência é obrigatório")
	@Column(name= "INICIOVIGENCIA")
	private Long inicioVigencia;
	
	@Min(value = 1, message="Termino da vigência deve ser maior que 0")
	@NotNull(message = "Termino da vigência é obrigatório")
	@Column(name= "TERMINOVIGENCIA")
	private Long terminoVigencia;
	
	private boolean deletado = false;
	
	@Column(name= "DATACADASTRO")
	private Long dataCadastro = System.currentTimeMillis();
	
	public void setInicioVigencia(Long inicioVigencia) {
		this.inicioVigencia = Util.truncMilliseconds(inicioVigencia);
	}
	
	public void setTerminoVigencia(Long terminoVigencia) {
		this.terminoVigencia = Util.truncMilliseconds(terminoVigencia);
	}
	
	@Transient
	public Campanha deletar() {
		this.deletado = true;
		
		return this;
	}
	
	public Campanha() {
		
	}
	
	public Campanha (CampanhaDTOReceive campanhaDTO) {
		this.setNome(campanhaDTO.getNome());
		this.setIdTimeCoracao(campanhaDTO.getIdTimeCoracao());
		this.setInicioVigencia(campanhaDTO.getInicioVigencia());
		this.setTerminoVigencia(campanhaDTO.getTerminoVigencia());
	}
	
	@Transient
	public Campanha aumentarDiaTerminoVigencia() {
		this.setTerminoVigencia(this.getTerminoVigencia() + DIA_MILLISEGUNDO);
		return this;
	}
}
