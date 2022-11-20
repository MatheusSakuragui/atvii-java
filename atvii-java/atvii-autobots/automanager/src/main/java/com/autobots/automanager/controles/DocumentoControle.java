package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLink;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired 
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;
	
	@GetMapping("/documentos/{clienteId}")
	public ResponseEntity<List<Documento>> documentosCliente(@PathVariable Long clienteId){
		Cliente alvo = repositorioCliente.getById(clienteId);
		if (alvo == null) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(alvo);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<List<Documento>>(alvo.getDocumentos(),HttpStatus.FOUND);
			return resposta;
		}

	}
	
	@PutMapping("/cadastrar/{clienteId}")
	public ResponseEntity<?> atualizarDocumento(@PathVariable Long clienteId,  @RequestBody Documento novoDocumento){
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente alvo = repositorioCliente.getById(clienteId);
		if (alvo != null) {
			alvo.getDocumentos().add(novoDocumento);
			repositorioCliente.save(alvo);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	
	@DeleteMapping("/excluir/{clienteId}")
	public ResponseEntity<?> excluirDocumento(@PathVariable Long clienteId, @RequestBody Documento documentoExclusao ) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente alvo = repositorioCliente.getById(clienteId);
		if (alvo != null) {
			alvo.getDocumentos().remove(alvo.getDocumentos().indexOf(documentoExclusao));
			repositorioCliente.save(alvo);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);

	}
}