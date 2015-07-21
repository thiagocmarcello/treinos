package br.com.novotreino.dao;

import java.util.List;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Aluno;

public class AlunoDAO extends BaseDAO<Aluno> {

	private static final long serialVersionUID = 1L;

	public AlunoDAO() {
		super(Aluno.class);
	}

	public List<Aluno> obterTodosPorAcademia(Academia academia) throws BaseDAOException {
		return getEm().createQuery("select a from Aluno a where a.academia = :_academia", Aluno.class)
				.setParameter("_academia", academia)
				.getResultList();
	}
}
