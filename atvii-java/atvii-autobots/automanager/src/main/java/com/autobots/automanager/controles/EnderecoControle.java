package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired 
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/endereco/{clienteId}")
	public Endereco obterEndereco(@PathVariable Long clienteId){
		Cliente alvo = repositorioCliente.getById(clienteId);
		return alvo.getEndereco();
	}
		
	@PutMapping("/atualizar/{clienteId}")
	public void atualizarDocumento(@PathVariable Long clienteId,  @RequestBody Endereco novoEndereco){
		Cliente alvo = repositorioCliente.getById(clienteId);
		alvo.setEndereco(novoEndereco);
		repositorioCliente.save(alvo);
		
	}
	
}
