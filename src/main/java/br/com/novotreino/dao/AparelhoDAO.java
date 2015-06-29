package br.com.novotreino.dao;

import br.com.novotreino.entidade.Aparelho;


public class AparelhoDAO extends BaseDAO<Aparelho> {

	private static final long serialVersionUID = 1L;

	public AparelhoDAO() {
		super(Aparelho.class);
	}

	public int verificarConsistenciaAparelho(Aparelho aparelho) throws BaseDAOException {
		return getEm().createQuery("select ex from Exercicio ex"
				+ " where ex.aparelho = :_aparelho")
				.setParameter("_aparelho", aparelho)
				.getResultList().size();
	}

}
