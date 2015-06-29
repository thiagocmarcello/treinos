package br.com.novotreino.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.AparelhoDAO;
import br.com.novotreino.dao.BaseDAOException;
import br.com.novotreino.entidade.Aparelho;

@Stateless
public class AparelhoServico extends BaseServico<Aparelho> {

	private static final long serialVersionUID = 1L;
	@Inject
	private AparelhoDAO aparelhoDAO;
	private Aparelho aparelho;

	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(aparelhoDAO);
	}

	public boolean deletar(Aparelho aparelho) throws BaseServicoException {
		this.aparelho = aparelho;
		try {
			if (verificarConsistenciaAparelho()) {

				deletar(aparelho, aparelho.getId());

				return true;
			} else {
				return false;
			}
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	private boolean verificarConsistenciaAparelho() {
		int quantidadeAparelho = aparelhoDAO
				.verificarConsistenciaAparelho(aparelho);
		if (quantidadeAparelho > 0) {
			return false;
		} else {
			return true;
		}
	}
}
