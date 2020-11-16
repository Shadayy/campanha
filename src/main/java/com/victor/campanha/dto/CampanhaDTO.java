package com.victor.campanha.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.victor.campanha.entity.Campanha;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CampanhaDTO {
	
	@ApiModelProperty(required = true)
	@NotBlank(message = "Nome da campanha é obrigatório")
	@Size(max = 255, message = "O nome da campanha é limitado em 255 caracteres")
	private String nome;
	
	@ApiModelProperty(required = true)
	@NotNull(message = "ID do Time do Coração é obrigatório")
	private Long idTimeCoracao;
	
	@Min(value = 1, message="Inicio da vigência deve ser maior que 0")
	@NotNull(message = "Inicio da vigência é obrigatório")
	@ApiModelProperty(required = true)
	private Long inicioVigencia;
	
	@Min(value = 1, message="Termino da vigência deve ser maior que 0")
	@NotNull(message = "Termino da vigência é obrigatório")
	@ApiModelProperty(required = true)
	private Long terminoVigencia;
	
	public CampanhaDTO() {
		
	}
	
	public CampanhaDTO(Campanha campanha){
		setNome(campanha.getNome());
		setIdTimeCoracao(campanha.getIdTimeCoracao());
		setInicioVigencia(campanha.getInicioVigencia());
		setTerminoVigencia(campanha.getTerminoVigencia());
	}

}
