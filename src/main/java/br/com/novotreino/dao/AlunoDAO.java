package br.com.novotreino.dao;

import br.com.novotreino.entidade.Aluno;

public class AlunoDAO extends BaseDAO<Aluno> {

	private static final long serialVersionUID = 1L;

	public AlunoDAO() {
		super(Aluno.class);
	}

}
