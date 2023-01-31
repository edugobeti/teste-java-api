package com.edugobeti.gerenciamentodepessoas.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edugobeti.gerenciamentodepessoas.domain.Pessoa;
import com.edugobeti.gerenciamentodepessoas.dto.PessoaDTO;
import com.edugobeti.gerenciamentodepessoas.exceptions.EntidadeNaoEncontradaException;
import com.edugobeti.gerenciamentodepessoas.repository.PessoaRepository;

import jakarta.transaction.Transactional;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Transactional
	public Pessoa criar(PessoaDTO pessoaDTO) {
		Pessoa novaPessoa = deDTO(pessoaDTO);
		return pessoaRepository.save(novaPessoa);
	}
	
	@Transactional
	public Pessoa alterarDados(PessoaDTO pessoaDTO) throws EntidadeNaoEncontradaException{
		Pessoa pessoa = pessoaRepository.findById(pessoaDTO.getId()).orElseThrow(
			() -> new  EntidadeNaoEncontradaException("Pessoa nao cadastrada"));
		pessoa = renovaPessoa(pessoa, pessoaDTO);
		return pessoaRepository.save(pessoa);
	}

	public PessoaDTO buscarPorId(Long id) throws EntidadeNaoEncontradaException{
		return paraDTO(pessoaRepository.findById(id).orElseThrow(
			()-> new EntidadeNaoEncontradaException("Pessoa nao cadastrada")));
	}
	
	public List<PessoaDTO> listarPessoa() {
		List<Pessoa> lista = pessoaRepository.findAll();
		List<PessoaDTO> listaPessoaDTO = lista.stream().map(p -> paraDTO(p)).collect(Collectors.toList());
		return listaPessoaDTO;
	}
	
	public PessoaDTO paraDTO(Pessoa pessoa) {
		PessoaDTO pessoaDTO = new PessoaDTO(pessoa);
		return pessoaDTO;
	}
	
	public Pessoa deDTO(PessoaDTO dto) {
		Pessoa pessoa = new Pessoa();
		pessoa.setId(dto.getId());
		pessoa.setNome(dto.getNome());
		pessoa.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));
		return pessoa;
	}

	public Pessoa renovaPessoa(Pessoa pessoa, PessoaDTO dto) {
		pessoa.setNome(dto.getNome());
		pessoa.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));
		return pessoa;
	}

	

}
