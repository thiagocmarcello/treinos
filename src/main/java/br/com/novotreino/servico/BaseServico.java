package br.com.novotreino.servico;

import java.util.List;

import br.com.novotreino.dao.BaseDAOException;
import br.com.novotreino.dao.InterfaceDAO;

public abstract class BaseServico<T> implements InterfaceServico<T> {

	private static final long serialVersionUID = 1L;

	private InterfaceDAO<T> dao;

	protected abstract void inicializar();

	@Override
	public int obterQuantidade() throws BaseServicoException {
		return dao.obterQuantidade();
	}

	@Override
	public T salvar(T t) throws BaseServicoException {
		try {
			return dao.salvar(t);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());	
		}
	}

	@Override
	public T alterar(T t) throws BaseServicoException {
		try {
			return dao.alterar(t);
		} catch (BaseDAOException bde) {
			bde.printStackTrace();
			throw new BaseServicoException(bde.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	@Override
	public void deletar(T t, Integer id) throws BaseServicoException {
		try {
			dao.deletar(t, id);
		} catch (BaseDAOException e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	@Override
	public List<T> obterTodos() throws BaseServicoException {
		try {
			return dao.obterTodos();
		} catch (BaseDAOException e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	@Override
	public T obterPorId(Integer id) throws BaseServicoException {
		try {
			return dao.obterPorId(id);
		} catch (BaseDAOException e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServicoException(e.getMessage());
		}
	}

	public InterfaceDAO<T> getDao() {
		return dao;
	}

	public void setDao(InterfaceDAO<T> dao) {
		this.dao = dao;
	}
	
}
