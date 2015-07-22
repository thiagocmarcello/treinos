package br.com.novotreino.dao;

import java.util.List;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Metodologia;

public class MetodologiaDAO extends BaseDAO<Metodologia> {

	private static final long serialVersionUID = 1L;

	public MetodologiaDAO() {
		super(Metodologia.class);
	}

	public int validarConsistenciametodologia(Metodologia metodologia) throws BaseDAOException {
		return getEm().createQuery("select me from Metodologia me"
				+ " join me.treinos tr"
				+ " where me = :_metodologia")
				.setParameter("_metodologia", metodologia)
				.getResultList().size();
	}

	public List<Metodologia> obterTodosPorAcademia(Academia academia) throws BaseDAOException {
		return getEm().createQuery("select m from Metodologia m where m.academia = :_academia", Metodologia.class)
				.setParameter("_academia", academia)
				.getResultList();
	}

}
