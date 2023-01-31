package com.edugobeti.gerenciamentodepessoas.teste;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.edugobeti.gerenciamentodepessoas.domain.Pessoa;
import com.edugobeti.gerenciamentodepessoas.dto.PessoaDTO;
import com.edugobeti.gerenciamentodepessoas.exceptions.EntidadeNaoEncontradaException;
import com.edugobeti.gerenciamentodepessoas.repository.PessoaRepository;
import com.edugobeti.gerenciamentodepessoas.service.PessoaService;

@SpringBootTest
public class PessoaServiceTest {
	
	private static final Integer INDEX = 0;
	private static final Long ID = 1L;
	private static final String NOME = "Carlos Ferreira";
	private static final String DATA_NASCIMENTO = "1983-01-09";
	private static final LocalDate DATA_NASCIMENTO_LD = LocalDate.parse(DATA_NASCIMENTO);
	
	private static final String ENTIDADE_NAO_ENCONTRADA = "Pessoa nao cadastrada";
	
	@InjectMocks
	private PessoaService pessoaService;
	
	@Mock
	private PessoaRepository pessoaRepository;
	
	private PessoaDTO pessoaDTO;
	private Pessoa pessoa;
	private Optional<Pessoa> optionalPessoa;
	
	@BeforeEach
	public void BeforeEach() {
		MockitoAnnotations.openMocks(this);
        startPessoa();
	}
	
	@Test
	public void QuandoCriarRetornaOk() {
		when(pessoaRepository.save(any())).thenReturn(pessoa);
	
	    Pessoa pessoaSalva = pessoaService.criar(pessoaDTO);
		
	    Assertions.assertNotNull(pessoaSalva);
	    Assertions.assertEquals(Pessoa.class, pessoaSalva.getClass());
		Assertions.assertEquals(NOME, pessoaSalva.getNome());
		Assertions.assertEquals(DATA_NASCIMENTO_LD, pessoaSalva.getDataNascimento());
	}
	
	@Test
	public void QuandoAtualizaDadosRetornaOk() {
		when(pessoaRepository.findById(anyLong())).thenReturn(optionalPessoa);
		when(pessoaRepository.save(any())).thenReturn(pessoa);
		
		Pessoa pessoaAtualizada = pessoaService.alterarDados(pessoaDTO);
		
	    Assertions.assertNotNull(pessoaAtualizada);
	    Assertions.assertEquals(Pessoa.class, pessoaAtualizada.getClass());
		Assertions.assertEquals(NOME, pessoaAtualizada.getNome());
		Assertions.assertEquals(DATA_NASCIMENTO_LD, pessoaAtualizada.getDataNascimento());
	}
	
	@Test
	public void QuandoBuscarPessoaPorIdRetornaOk() {
		when(pessoaRepository.findById(anyLong())).thenReturn(optionalPessoa);
		
        PessoaDTO pessoaBuscada = pessoaService.buscarPorId(pessoaDTO.getId());
		
	    Assertions.assertNotNull(pessoaBuscada);
	    Assertions.assertEquals(PessoaDTO.class, pessoaBuscada.getClass());
		Assertions.assertEquals(NOME, pessoaBuscada.getNome());
		Assertions.assertEquals(DATA_NASCIMENTO, pessoaBuscada.getDataNascimento());
	}
	
	@Test 
	public void QuandoBuscaPorIdRetornaEntidadeNaoEncontradaException() {
		when(pessoaRepository.findById(anyLong()))
			.thenThrow(new EntidadeNaoEncontradaException(ENTIDADE_NAO_ENCONTRADA));
		
		try {
			pessoaService.buscarPorId(2L);
		}
		catch (Exception e) {
			Assertions.assertEquals(EntidadeNaoEncontradaException.class, e.getClass());
			Assertions.assertEquals(ENTIDADE_NAO_ENCONTRADA, e.getMessage());
		}
		
		Assertions.assertThrows(EntidadeNaoEncontradaException.class, 
				() -> pessoaService.alterarDados(pessoaDTO));
	}
	
	@Test
	public void QuandoListarPessoasRetornaListaDePessoas() {
		when(pessoaRepository.findAll()).thenReturn(List.of(pessoa));
		
		List<PessoaDTO> listaPessoaDTO = pessoaService.listarPessoa();
	
		Assertions.assertNotNull(listaPessoaDTO);
		Assertions.assertEquals(1, listaPessoaDTO.size());
		Assertions.assertEquals(PessoaDTO.class, listaPessoaDTO.get(INDEX).getClass());
	
		Assertions.assertEquals(ID, listaPessoaDTO.get(INDEX).getId());
		Assertions.assertEquals(NOME, listaPessoaDTO.get(INDEX).getNome());
		Assertions.assertEquals(DATA_NASCIMENTO, listaPessoaDTO.get(INDEX).getDataNascimento());
	}
	
	@Test
	public void QuandoDeDTORetornaPessoa() {
		
		Pessoa retorno = pessoaService.deDTO(pessoaDTO);
		
		Assertions.assertNotNull(retorno);
		Assertions.assertEquals(Pessoa.class, retorno.getClass());
		Assertions.assertEquals(ID, retorno.getId());
		Assertions.assertEquals(NOME, retorno.getNome());
		Assertions.assertEquals(DATA_NASCIMENTO_LD, retorno.getDataNascimento());
	}
	
	@Test
	public void QuandoParaDTORetornaPessoaDTO() {
		
		PessoaDTO retorno = pessoaService.paraDTO(pessoa);
		
		Assertions.assertNotNull(retorno);
		Assertions.assertEquals(PessoaDTO.class, retorno.getClass());
		Assertions.assertEquals(ID, retorno.getId());
		Assertions.assertEquals(NOME, retorno.getNome());
		Assertions.assertEquals(DATA_NASCIMENTO, retorno.getDataNascimento());
	}
	
	@Test
	public void QuandoRenovaPessoaRetornaAtualizada() {
		
		Pessoa pessoa1 = new Pessoa(1L, "Carol Senna", LocalDate.parse("1994-05-25"), null);
		Pessoa pessoaAtualizada = pessoaService.renovaPessoa(pessoa1, pessoaDTO);
		
		Assertions.assertNotNull(pessoaAtualizada);
		Assertions.assertEquals(Pessoa.class, pessoaAtualizada.getClass());
		Assertions.assertEquals(ID, pessoaAtualizada.getId());
		Assertions.assertEquals(NOME, pessoaAtualizada.getNome());
		Assertions.assertEquals(DATA_NASCIMENTO_LD, pessoaAtualizada.getDataNascimento());
	}
	
	private void startPessoa() {
		pessoaDTO = new PessoaDTO(ID, NOME, DATA_NASCIMENTO);
		pessoa = new Pessoa(ID, NOME, DATA_NASCIMENTO_LD, null);
		optionalPessoa = Optional.of(new Pessoa(ID, NOME, DATA_NASCIMENTO_LD, null));
	}
}
