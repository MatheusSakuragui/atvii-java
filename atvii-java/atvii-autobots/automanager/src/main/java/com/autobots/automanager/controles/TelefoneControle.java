package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired 
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/telefones/{clienteId}")
	public List<Telefone> telefonesCliente(@PathVariable Long clienteId){
		Cliente alvo = repositorioCliente.getById(clienteId);
		return alvo.getTelefones();
	}
	
	@PutMapping("/cadastrar/{clienteId}")
	public void atualizarDocumento(@PathVariable Long clienteId,  @RequestBody Telefone novoTelefone){
		Cliente alvo = repositorioCliente.getById(clienteId);
		alvo.getTelefones().add(novoTelefone);
		repositorioCliente.save(alvo);
		
	}
	
	@DeleteMapping("/excluir/{clienteId}")
	public void excluirDocumento(@PathVariable Long clienteId, @RequestBody Telefone telefoneExclusao ) {
		Cliente alvo = repositorioCliente.getById(clienteId);
		alvo.getTelefones().remove(alvo.getTelefones().indexOf(telefoneExclusao));
		repositorioCliente.save(alvo);
	}
}
