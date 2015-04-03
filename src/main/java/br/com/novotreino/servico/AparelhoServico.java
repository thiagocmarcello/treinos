package br.com.novotreino.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.AparelhoDAO;
import br.com.novotreino.entidade.Aparelho;

@Stateless
public class AparelhoServico extends BaseServico<Aparelho> {

	private static final long serialVersionUID = 1L;
	@Inject
	private AparelhoDAO aparelhoDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(aparelhoDAO);
	}

}
