package br.com.novotreino.servico;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.novotreino.dao.AcademiaDAO;
import br.com.novotreino.entidade.Academia;

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

	public List<SelectItem> obterTodosSelectItem() throws BaseServicoException {
		List<SelectItem> si = new ArrayList<SelectItem>();
		for (Academia a : obterTodos()) {
			si.add(new SelectItem(a.getId(), a.getNome()));
		}
		return si;
	}
}
