package br.com.novotreino.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.BaseDAOException;
import br.com.novotreino.dao.TreinoDAO;
import br.com.novotreino.entidade.Metodologia;
import br.com.novotreino.entidade.Treino;

@Stateless
public class TreinoServico extends BaseServico<Treino> {

	private static final long serialVersionUID = 1L;

	@Inject
	private TreinoDAO treinoDAO;

	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(treinoDAO);
	}

	public List<Treino> obterPorMetodologia(Metodologia metodologia)
			throws BaseServicoException {
		return treinoDAO.obterPorMetodologia(metodologia);
	}

	private Treino treino;

	public boolean deletar(Treino treino) throws BaseServicoException {
		this.treino = treino;
		if (validarExclusao()) {
			treinoDAO.deletar(treino, treino.getId());
			return true;
		} else {
			return false;
		}
	}

	private boolean validarExclusao() throws BaseServicoException {
		try {
			int quantidade = treinoDAO.validarExclusao(treino);
			if (quantidade > 0) {
				return false;
			} else {
				return true;
			}
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
