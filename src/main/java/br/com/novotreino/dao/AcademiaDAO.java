package br.com.novotreino.dao;

import java.util.List;

import br.com.novotreino.entidade.Academia;

public class AcademiaDAO extends BaseDAO<Academia> {

	private static final long serialVersionUID = 1L;

	public AcademiaDAO() {
		super(Academia.class);
	}

	public int verificarConsistenciaAcademiaAluno(Academia academia) throws BaseDAOException {
		return getEm().createQuery("select ac from Academia ac"
				+ " join ac.alunos al"
				+ " where ac = :_academia")
				.setParameter("_academia", academia)
				.getResultList().size();
	}

	public List<Academia> obterTodosPorAcademia(Academia academia) {
		return getEm().createQuery("select", Academia.class)
				.setParameter("_academia", academia)
				.getResultList();
	}

}
