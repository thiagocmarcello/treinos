package br.com.novotreino.dao;

import java.util.List;

import br.com.novotreino.entidade.Cidade;
import br.com.novotreino.servico.BaseServicoException;

public class CidadeDAO extends BaseDAO<Cidade> {

	private static final long serialVersionUID = 1L;

	public CidadeDAO() {
		super(Cidade.class);
	}

	public List<Cidade> obterPorEstado(Integer idEstado)
			throws BaseServicoException {
		return getEm().createNativeQuery("select * from cidade "
				+ "where _estado = :_idEstado", Cidade.class)
				.setParameter("_idEstado", idEstado)
				.getResultList();
	}
	
	public Cidade obterPorId(Integer id) {
		return (Cidade) getEm().createNativeQuery("select * from cidade "
				+ "where id = :_id", Cidade.class)
				.setParameter("_id", id)
				.getSingleResult();
	}

}
