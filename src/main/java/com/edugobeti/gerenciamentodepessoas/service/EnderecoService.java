package com.edugobeti.gerenciamentodepessoas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edugobeti.gerenciamentodepessoas.domain.Endereco;
import com.edugobeti.gerenciamentodepessoas.domain.Pessoa;
import com.edugobeti.gerenciamentodepessoas.dto.EnderecoDTO;
import com.edugobeti.gerenciamentodepessoas.enuns.TipoEndereco;
import com.edugobeti.gerenciamentodepessoas.exceptions.EntidadeNaoEncontradaException;
import com.edugobeti.gerenciamentodepessoas.repository.EnderecoRepository;
import com.edugobeti.gerenciamentodepessoas.repository.PessoaRepository;

import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Transactional
	public Endereco criar(EnderecoDTO enderecoDTO) {
		Pessoa pessoa = pessoaRepository.findById(enderecoDTO.getPessoaId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa nao cadastrada"));
		EnderecoDTO endDTO = enderecoDTO;
		if(pessoa.getEnderecos().isEmpty()) {
			enderecoRepository.save(deDTO(endDTO));
		}
		if(!pessoa.getEnderecos().isEmpty()) {
			EnderecoDTO testEndDTO = buscarEnderecoPrincipal(enderecoDTO.getPessoaId());
			if(testEndDTO.getEnderecoPricipal().equals(1) && endDTO.getEnderecoPricipal().equals(1)) {
				testEndDTO.setEnderecoPricipal(2);
				enderecoRepository.save(deDTO(testEndDTO));
			}
			enderecoRepository.save(deDTO(enderecoDTO));
		}
		return deDTO(enderecoDTO);
	}

	public List<EnderecoDTO> listarEndereco(Long pessoaId) {
		List<Endereco> lista = enderecoRepository.findAll();
		List<EnderecoDTO> listaEnderecoDTO = lista.stream()
				.filter(p -> p.getPessoa().getId().equals(pessoaId))
				.map(p -> paraDTO(p)).collect(Collectors.toList());
		if(listaEnderecoDTO.isEmpty()) {
			throw new EntidadeNaoEncontradaException("Pessoa sem endereco cadastrado");
		}
		return listaEnderecoDTO;
	}

	public EnderecoDTO buscarEnderecoPrincipal(Long pessoaId) {
		List<EnderecoDTO> listaDTO = listarEndereco(pessoaId);
		return listaDTO.stream()
				.filter(p -> p.getPessoaId().equals(pessoaId))
			    .filter(p -> p.getEnderecoPricipal().equals(1))
			    .findAny()
			    .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa sem endereco cadastrado"));
	}
	
	public EnderecoDTO paraDTO(Endereco endereco) {
		EnderecoDTO enderecoDTO = new EnderecoDTO(endereco);
		enderecoDTO.setPessoaId(endereco.getPessoa().getId());
		return enderecoDTO;
	}

	public Endereco deDTO(EnderecoDTO dto) {
		Pessoa pessoa = pessoaRepository.findById(dto.getPessoaId()).get();
		Endereco endereco = new Endereco();
		endereco.setId(dto.getId());
		endereco.setLogradouro(dto.getLogradouro());
		endereco.setCep(dto.getCep());
		endereco.setNumero(dto.getNumero());
		endereco.setCidade(dto.getCidade());
		endereco.setTipoEndereco(TipoEndereco.toEnum(dto.getEnderecoPricipal()));
		endereco.setPessoa(pessoa);
		return endereco;
	}
}
