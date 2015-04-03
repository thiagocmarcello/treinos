package br.com.novotreino.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.AlunoDAO;
import br.com.novotreino.entidade.Aluno;


@Stateless
public class AlunoServico extends BaseServico<Aluno> {

	private static final long serialVersionUID = 1L;
	@Inject
	private AlunoDAO alunoDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(alunoDAO);
	}
}
