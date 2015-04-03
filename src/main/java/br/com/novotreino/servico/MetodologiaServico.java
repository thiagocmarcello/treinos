package br.com.novotreino.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.MetodologiaDAO;
import br.com.novotreino.entidade.Metodologia;

@Stateless
public class MetodologiaServico extends BaseServico<Metodologia> {

	private static final long serialVersionUID = 1L;
	@Inject
	private MetodologiaDAO metodologiaDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(metodologiaDAO);
	}

}
