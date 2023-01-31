package com.edugobeti.gerenciamentodepessoas.service;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edugobeti.gerenciamentodepessoas.domain.Endereco;
import com.edugobeti.gerenciamentodepessoas.domain.Pessoa;
import com.edugobeti.gerenciamentodepessoas.enuns.TipoEndereco;
import com.edugobeti.gerenciamentodepessoas.repository.EnderecoRepository;
import com.edugobeti.gerenciamentodepessoas.repository.PessoaRepository;

@Service
public class DataBaseSevice {

	@Autowired
	private PessoaRepository pessoaReporitory;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public void InstanciamentoDB(){	
		
		Pessoa p1 = new Pessoa(null, "Joana Silva", LocalDate  .parse("1974-09-11"), null);
		Pessoa p2 = new Pessoa(null, "Ricardo Oliveira", LocalDate.parse("1981-11-04"), null);
		Pessoa p3 = new Pessoa(null, "Joana Silva", LocalDate.parse("1969-07-30"), null);
	
		Endereco end1 = new Endereco(null, "Rua XV de Novembro", "13453583", "1025", "Campinas", null, null);
		Endereco end2 = new Endereco(null, "Avenida do Estado", "01251693", "3250", "Sao Paulo", null, null);
		Endereco end3 = new Endereco(null, "Rua Dona Margarida", "13450000", "430", "Piracicaba", null, null);
		Endereco end4 = new Endereco(null, "Rua Carlos Xavier", "44125487", "120", "Brasilia", null, null);
		Endereco end5 = new Endereco(null, "Rodovia Bandeirantes", "13250000", "km 125 Sul", "Americana", null, null);
		Endereco end6 = new Endereco(null, "Rua Santos Dumont", "33458120", "1240", "Curitiba", null, null);
		
		p1.setEnderecos(Arrays.asList(end1, end2));
		p2.setEnderecos(Arrays.asList(end3, end4, end5));
		p3.setEnderecos(Arrays.asList(end6));
		
		end1.setPessoa(p1);
		end2.setPessoa(p1);
		end3.setPessoa(p2);
		end4.setPessoa(p2);
		end5.setPessoa(p2);
		end6.setPessoa(p3);
		
		end1.setTipoEndereco(TipoEndereco.PRINCIPAL);
		end2.setTipoEndereco(TipoEndereco.RECADO);
		end3.setTipoEndereco(TipoEndereco.PRINCIPAL);
		end4.setTipoEndereco(TipoEndereco.TRABALHO);
		end5.setTipoEndereco(TipoEndereco.RECADO);
		end6.setTipoEndereco(TipoEndereco.PRINCIPAL);
		
		pessoaReporitory.saveAll(Arrays.asList(p1, p2, p3));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3, end4, end5, end6));
	}
}
