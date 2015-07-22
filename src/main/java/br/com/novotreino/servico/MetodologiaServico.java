package br.com.novotreino.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import sun.nio.ch.BsdAsynchronousChannelProvider;
import br.com.novotreino.dao.BaseDAOException;
import br.com.novotreino.dao.MetodologiaDAO;
import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Metodologia;

@Stateless
public class MetodologiaServico extends BaseServico<Metodologia> {

	private static final long serialVersionUID = 1L;
	@Inject
	private MetodologiaDAO metodologiaDAO;
	private Metodologia metodologia;

	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(metodologiaDAO);
	}

	public boolean deletar(Metodologia metodologia) throws BaseServicoException {
		this.metodologia = metodologia;
		if (validarConsistenciaMetodologia()) {
			metodologiaDAO.deletar(metodologia, metodologia.getId());
			return true;
		} else {
			return false;
		}
	}

	private boolean validarConsistenciaMetodologia()
			throws BaseServicoException {
		try {
			int quantidadeMetodologia = metodologiaDAO
					.validarConsistenciametodologia(metodologia);
			if (quantidadeMetodologia > 0) {
				return false;
			} else {
				return true;
			}
		} catch (BaseDAOException e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	public List<Metodologia> obterTodosPorAcademia(Academia academia) throws BaseServicoException {
		return metodologiaDAO.obterTodosPorAcademia(academia);
	}
}
