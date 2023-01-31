package com.edugobeti.gerenciamentodepessoas.dto;

import com.edugobeti.gerenciamentodepessoas.domain.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class PessoaDTO {

	private Long id;
	
	@NotBlank(message = "Preencimento obrigatorio")
	@Size(max = 60)
	private String nome;
	
	@NotBlank(message = "Preencimento obrigatorio")
	@JsonFormat(pattern="dd/MM/yyyy")
	private String dataNascimento;

	public PessoaDTO(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.nome = pessoa.getNome();
		this.dataNascimento = pessoa.getDataNascimento().toString();
	}
}
