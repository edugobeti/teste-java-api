package com.edugobeti.gerenciamentodepessoas.dto;

import com.edugobeti.gerenciamentodepessoas.domain.Endereco;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnderecoDTO {

	private Long id;
	
	@NotBlank(message = "Preencimento obrigatorio")
	private Long pessoaId;
	
	@NotBlank(message = "Preencimento obrigatorio")
	private String logradouro;
	
	@NotBlank(message = "Preencimento obrigatorio")
	private String cep;
	
	@NotBlank(message = "Preencimento obrigatorio")
	private String numero;
	
	@NotBlank(message = "Preencimento obrigatorio")
	private String cidade;

	private Integer enderecoPricipal;
	
	public EnderecoDTO(Endereco endereco) {
		this.id = endereco.getId();
		this.pessoaId = endereco.getPessoa().getId();
		this.logradouro = endereco.getLogradouro();
		this.cep = endereco.getCep();
		this.numero = endereco.getNumero();
		this.cidade = endereco.getCidade();
		this.enderecoPricipal = endereco.getTipoEndereco().getCod();
	}
	
}
