package br.com.novotreino.dao;

import java.util.List;

import br.com.novotreino.entidade.Metodologia;
import br.com.novotreino.entidade.Treino;

public class TreinoDAO extends BaseDAO<Treino> {

	private static final long serialVersionUID = 1L;

	public TreinoDAO() {
		super(Treino.class);
	}

	public List<Treino> obterPorMetodologia(Metodologia metodologia)
			throws BaseDAOException {
		return getEm()
				.createQuery("select t from Treino t"
						+ " where t.metodologia = :_metodologia")
				.setParameter("_metodologia", metodologia)
				.getResultList();
	}

}
