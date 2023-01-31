package com.edugobeti.gerenciamentodepessoas.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edugobeti.gerenciamentodepessoas.dto.EnderecoDTO;
import com.edugobeti.gerenciamentodepessoas.dto.PessoaDTO;
import com.edugobeti.gerenciamentodepessoas.service.EnderecoService;
import com.edugobeti.gerenciamentodepessoas.service.PessoaService;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private EnderecoService enderecoService;
	
	
	@PostMapping
	public ResponseEntity<Void> inserirPessoa(@RequestBody PessoaDTO pessoaDTO){
		pessoaService.criar(pessoaDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pessoaDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{pessoaId}")
	public ResponseEntity<Void> alterarDados(@PathVariable Long pessoaId, @RequestBody PessoaDTO pessoaDTO){
		pessoaDTO.setId(pessoaId);
		pessoaService.alterarDados(pessoaDTO);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{pessoaId}")
	public ResponseEntity<PessoaDTO> buscarPorId(@PathVariable Long pessoaId){
		PessoaDTO pessoa = pessoaService.buscarPorId(pessoaId);
		return ResponseEntity.ok().body(pessoa);
	}
	
	@GetMapping
	public ResponseEntity<List<PessoaDTO>> listarPessoa(){
		List<PessoaDTO> listaPessoaDTO = pessoaService.listarPessoa();
		return ResponseEntity.ok().body(listaPessoaDTO);
	}
	

	@PostMapping(value = "/{pessoaId}/enderecos")
	public ResponseEntity<Void> inserirEndereco(@PathVariable Long pessoaId, @RequestBody EnderecoDTO enderecoDTO){
		enderecoDTO.setPessoaId(pessoaId);
		enderecoService.criar(enderecoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(enderecoDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping(value = "/{pessoaId}/enderecos")
	public ResponseEntity<List<EnderecoDTO>> listarEndereco(@PathVariable Long pessoaId){
		List<EnderecoDTO> listaEnderecoDTO = enderecoService.listarEndereco(pessoaId);
		return ResponseEntity.ok().body(listaEnderecoDTO);
	}
	
	@GetMapping(value = "/{pessoaId}/principal")
	public ResponseEntity<EnderecoDTO> retornaEndPrincipal(@PathVariable Long pessoaId){
		EnderecoDTO enderecoPrincipal = enderecoService.buscarEnderecoPrincipal(pessoaId);
		return ResponseEntity.ok().body(enderecoPrincipal);
	}
	
	
	
	
	
	
	
	
	
	
}
