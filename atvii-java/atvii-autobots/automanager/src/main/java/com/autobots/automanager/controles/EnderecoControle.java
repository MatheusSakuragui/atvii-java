package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;
	@Autowired 
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/endereco/{clienteId}")
	public  ResponseEntity<Endereco> obterEndereco(@PathVariable Long clienteId){
		Cliente alvo = repositorioCliente.getById(clienteId);
		if(alvo == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		}else {
			adicionadorLink.adicionarLink(alvo);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(alvo.getEndereco(),HttpStatus.FOUND);
			return resposta;
		}
		
	}
		
	@PutMapping("/atualizar/{clienteId}")
	public ResponseEntity<?> atualizarDocumento(@PathVariable Long clienteId,  @RequestBody Endereco novoEndereco){
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente alvo = repositorioCliente.getById(clienteId);
		if (alvo != null) {
			alvo.setEndereco(novoEndereco);
			repositorioCliente.save(alvo);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
}
