package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.novotreino.entidade.Metodologia;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.MetodologiaServico;

@Named
@ViewScoped
public class MetodologiaController extends BaseController<Metodologia>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MetodologiaServico metodologiaServico;

	private List<Metodologia> metodologias;
	private Metodologia metodologia;

	@Override
	protected Class<Metodologia> getManagedClass() {
		return Metodologia.class;
	}

	@Override
	@PostConstruct
	public void inicializar() {
		metodologia = new Metodologia();
		carregarMetodologias();
	}

	private void carregarMetodologias() {
		try {
			metodologias = metodologiaServico.obterTodos();
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String salvar() {
		try {
			if (metodologia.getId() != null) {
				metodologiaServico.alterar(metodologia);
			} else {
				metodologiaServico.salvar(metodologia);
			}
			setIndexTab(1);
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inicializar();
		return null;
	}

	@Override
	public void deletar(Object obj) {
		Metodologia a = (Metodologia) obj;
		try {
			metodologiaServico.deletar(a, a.getId());
			inicializar();
			setIndexTab(1);
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Metodologia k) {
		metodologia = k;
		setIndexTab(0);
	}

	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List<Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	public Metodologia getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(Metodologia metodologia) {
		this.metodologia = metodologia;
	}
}
