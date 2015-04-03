package br.com.novotreino.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.EstadoDAO;
import br.com.novotreino.entidade.Estado;

@Stateless
public class EstadoServico extends BaseServico<Estado> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EstadoDAO estadoDao;

	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(estadoDao);
	}

	@Override
	public List<Estado> obterTodos() throws BaseServicoException {
		return estadoDao.obterTodos();
	}

}
