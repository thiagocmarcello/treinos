package br.com.novotreino.dao;

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

}
