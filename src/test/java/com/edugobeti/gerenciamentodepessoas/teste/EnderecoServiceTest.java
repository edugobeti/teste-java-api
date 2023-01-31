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

import com.edugobeti.gerenciamentodepessoas.domain.Endereco;
import com.edugobeti.gerenciamentodepessoas.domain.Pessoa;
import com.edugobeti.gerenciamentodepessoas.dto.EnderecoDTO;
import com.edugobeti.gerenciamentodepessoas.enuns.TipoEndereco;
import com.edugobeti.gerenciamentodepessoas.exceptions.EntidadeNaoEncontradaException;
import com.edugobeti.gerenciamentodepessoas.repository.EnderecoRepository;
import com.edugobeti.gerenciamentodepessoas.repository.PessoaRepository;
import com.edugobeti.gerenciamentodepessoas.service.EnderecoService;

@SpringBootTest
public class EnderecoServiceTest {
	
	private static final Integer INDEX = 0;
	private static final Long ID = 1L;
	private static final Long PESSOA_ID = 1L;
	private static final String LOGRADOURO = "Logradouro";
	private static final String CEP = "Cep" ;
	private static final String NUMERO = "Numero";
	private static final String CIDADE = "Cidade";
	private static final Integer ENDERECO_PRINCIPAL = 1;
	
	private static final String NOME = "Carlos Ferreira";
	private static final String DATA_NASCIMENTO = "1983-01-09";
	private static final LocalDate DATA_NASCIMENTO_LD = LocalDate.parse(DATA_NASCIMENTO);
	
	private static final String ENTIDADE_NAO_ENCONTRADA = "Pessoa sem endereco cadastrado";
	
	@InjectMocks
	private EnderecoService enderecoService;
	
	@Mock
	private EnderecoRepository enderecoRepository;
	
	@Mock
	private PessoaRepository pessoaRepository;
	
	private EnderecoDTO enderecoDTO;
	private Endereco endereco;
	
	private Pessoa pessoa;
	private Optional<Pessoa> optionalPessoa;
	
	@BeforeEach
	public void BeforeEach() {
		MockitoAnnotations.openMocks(this);
        startEndereco();
	}
	
	@Test
	public void QuandoCriarRetornaOk() {
		when(enderecoRepository.save(any())).thenReturn(endereco);
		when(pessoaRepository.findById(anyLong())).thenReturn(optionalPessoa);
		
	    Endereco enderecoSalvo = enderecoService.criar(enderecoDTO);
		
	    Assertions.assertNotNull(enderecoSalvo);
	    Assertions.assertEquals(Endereco.class, enderecoSalvo.getClass());
		Assertions.assertEquals(ID, enderecoSalvo.getId());
		Assertions.assertEquals(LOGRADOURO, enderecoSalvo.getLogradouro());
	}
	
	@Test
	public void QuandoBuscarenderecoPrincipalRetornaOk() {
		when(enderecoRepository.findAll()).thenReturn(List.of(endereco));
		
		EnderecoDTO endereco1 = enderecoService.buscarEnderecoPrincipal(1L);
		
		Assertions.assertNotNull(endereco1);
		Assertions.assertEquals(EnderecoDTO.class, endereco1.getClass());
	
		Assertions.assertEquals(ID, endereco1.getId());
		Assertions.assertEquals(PESSOA_ID, endereco1.getPessoaId());
		Assertions.assertEquals(LOGRADOURO, endereco1.getLogradouro());
		Assertions.assertEquals(CEP, endereco1.getCep());
		Assertions.assertEquals(NUMERO, endereco1.getNumero());
		Assertions.assertEquals(CIDADE, endereco1.getCidade());
		Assertions.assertEquals(ENDERECO_PRINCIPAL, endereco1.getEnderecoPricipal());
	}
	
	@Test 
	public void QuandoBuscarEnderecoPrincipalRetornaEntidadeNaoEncontradaException() {
		when(enderecoRepository.findById(anyLong()))
			.thenThrow(new EntidadeNaoEncontradaException(ENTIDADE_NAO_ENCONTRADA));
		
		try {
			enderecoService.buscarEnderecoPrincipal(2L);
		}
		catch (Exception e) {
			Assertions.assertEquals(EntidadeNaoEncontradaException.class, e.getClass());
			Assertions.assertEquals(ENTIDADE_NAO_ENCONTRADA, e.getMessage());
		}
		
		Assertions.assertThrows(EntidadeNaoEncontradaException.class, 
				() -> enderecoService.buscarEnderecoPrincipal(2L));
	}
	
	@Test
	public void QuandoListarEnderecosRetornaListaDeenderecos() {
		when(enderecoRepository.findAll()).thenReturn(List.of(endereco));
		when(pessoaRepository.findById(anyLong())).thenReturn(optionalPessoa);
		
		List<EnderecoDTO> listaenderecoDTO = enderecoService.listarEndereco(1L);
	
		Assertions.assertNotNull(listaenderecoDTO);
		Assertions.assertEquals(1, listaenderecoDTO.size());
		Assertions.assertEquals(EnderecoDTO.class, listaenderecoDTO.get(INDEX).getClass());
	
		Assertions.assertEquals(ID, listaenderecoDTO.get(INDEX).getId());
		Assertions.assertEquals(PESSOA_ID, listaenderecoDTO.get(INDEX).getPessoaId());
		Assertions.assertEquals(LOGRADOURO, listaenderecoDTO.get(INDEX).getLogradouro());
		Assertions.assertEquals(CEP, listaenderecoDTO.get(INDEX).getCep());
		Assertions.assertEquals(NUMERO, listaenderecoDTO.get(INDEX).getNumero());
		Assertions.assertEquals(CIDADE, listaenderecoDTO.get(INDEX).getCidade());
		Assertions.assertEquals(ENDERECO_PRINCIPAL, listaenderecoDTO.get(INDEX).getEnderecoPricipal());
	}
	
	@Test
	public void QuandoDeDTORetornaEndereco() {
		when(pessoaRepository.findById(anyLong())).thenReturn(optionalPessoa);
		
		Endereco retorno = enderecoService.deDTO(enderecoDTO);
		
		Assertions.assertNotNull(retorno);
		Assertions.assertEquals(Endereco.class, retorno.getClass());
		Assertions.assertEquals(ID, retorno.getId());
		Assertions.assertEquals(LOGRADOURO, retorno.getLogradouro());
		Assertions.assertEquals(CEP, retorno.getCep());
		Assertions.assertEquals(NUMERO, retorno.getNumero());
		Assertions.assertEquals(CIDADE, retorno.getCidade());
		Assertions.assertEquals(ENDERECO_PRINCIPAL, retorno.getTipoEndereco().getCod());
	}
	
	@Test
	public void QuandoParaDTORetornaEnderecoDTO() {
		
		EnderecoDTO retorno = enderecoService.paraDTO(endereco);
		
		Assertions.assertNotNull(retorno);
		Assertions.assertEquals(EnderecoDTO.class, retorno.getClass());
		Assertions.assertEquals(ID, retorno.getId());
		Assertions.assertEquals(LOGRADOURO, retorno.getLogradouro());
		Assertions.assertEquals(CEP, retorno.getCep());
		Assertions.assertEquals(NUMERO, retorno.getNumero());
		Assertions.assertEquals(CIDADE, retorno.getCidade());
		Assertions.assertEquals(ENDERECO_PRINCIPAL, retorno.getEnderecoPricipal());
	}
	
	private void startEndereco() {
		pessoa = new Pessoa(ID, NOME, DATA_NASCIMENTO_LD, List.of());
		enderecoDTO = new EnderecoDTO(ID, PESSOA_ID, LOGRADOURO, CEP, NUMERO, CIDADE, ENDERECO_PRINCIPAL);
		endereco = new Endereco(ID, LOGRADOURO, CEP, NUMERO, CIDADE, TipoEndereco.PRINCIPAL, pessoa);
		optionalPessoa = Optional.of(new Pessoa(ID, NOME, DATA_NASCIMENTO_LD, List.of()));
		
	}

}
