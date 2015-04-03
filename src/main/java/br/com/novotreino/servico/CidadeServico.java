package br.com.novotreino.servico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.CidadeDAO;
import br.com.novotreino.entidade.Cidade;

@Stateless
public class CidadeServico extends BaseServico<Cidade> {

	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeDAO cidadeDao;
	
	@Override
	protected void inicializar() {
		setDao(cidadeDao);
	}

	public List<Cidade> obterPorEstado(Integer idEstado) {
		try {
			return cidadeDao.obterPorEstado(idEstado);
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Cidade obterPorId(Integer id) {
		return cidadeDao.obterPorId(id);
	}

}
