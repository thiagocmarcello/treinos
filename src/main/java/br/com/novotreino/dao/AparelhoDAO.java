package br.com.novotreino.dao;

import java.util.List;

import br.com.novotreino.entidade.Academia;
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

	public List<Aparelho> obterTodosPorAcademia(Academia academia) throws BaseDAOException {
		return getEm().createQuery("select a from Aparelho a where a.academia = :_academia", Aparelho.class)
				.setParameter("_academia", academia)
				.getResultList();
	}

}
