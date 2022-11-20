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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired 
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/documentos/{clienteId}")
	public List<Documento> documentosCliente(@PathVariable Long clienteId){
		Cliente alvo = repositorioCliente.getById(clienteId);
		return alvo.getDocumentos();
	}
	
	@PutMapping("/cadastrar/{clienteId}")
	public void atualizarDocumento(@PathVariable Long clienteId,  @RequestBody Documento novoDocumento){
		Cliente alvo = repositorioCliente.getById(clienteId);
		alvo.getDocumentos().add(novoDocumento);
		repositorioCliente.save(alvo);
		
	}
	
	@DeleteMapping("/excluir/{clienteId}")
	public void excluirDocumento(@PathVariable Long clienteId, @RequestBody Documento documentoExclusao ) {
		Cliente alvo = repositorioCliente.getById(clienteId);
		alvo.getDocumentos().remove(alvo.getDocumentos().indexOf(documentoExclusao));
		repositorioCliente.save(alvo);
	}
}