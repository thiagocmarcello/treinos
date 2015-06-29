package br.com.novotreino.servico;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.novotreino.dao.AcademiaDAO;
import br.com.novotreino.entidade.Academia;
import br.com.novotreino.util.MensagemUtil;

@Stateless
public class AcademiaServico extends BaseServico<Academia> {

	private static final long serialVersionUID = 1L;
	@Inject
	private AcademiaDAO academiaDAO;
	
	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(academiaDAO);
	}
	
	private Academia academia;
	
	public boolean deletar(Academia academia) throws BaseServicoException {
		this.academia = academia;
		if (verificarConsistenciaAcademiaAluno()) {
		deletar(academia, academia.getId());
		return true;
		} else {
			return false;
		}
	}
	
	private boolean verificarConsistenciaAcademiaAluno() throws BaseServicoException {
		int quantidade = academiaDAO.verificarConsistenciaAcademiaAluno(academia);
		if (quantidade > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<SelectItem> obterTodosSelectItem() throws BaseServicoException {
		List<SelectItem> si = new ArrayList<SelectItem>();
		for (Academia a : obterTodos()) {
			si.add(new SelectItem(a.getId(), a.getNome()));
		}
		return si;
	}
}
