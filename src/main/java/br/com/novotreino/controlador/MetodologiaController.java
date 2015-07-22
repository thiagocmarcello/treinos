package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Metodologia;
import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EString;
import br.com.novotreino.servico.AcademiaServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.MetodologiaServico;
import br.com.novotreino.util.ControleUtil;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class MetodologiaController extends BaseController<Metodologia>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MetodologiaServico metodologiaServico;
	@EJB
	private AcademiaServico academiaServico;
	@Inject
	private LoginController loginController;
	@Inject
	private ControleUtil controleUtil;
	private List<Metodologia> metodologias;
	private Metodologia metodologia;

	private List<Academia> academias;
	private Academia academiaSelecionada;
	private Usuario usuarioSessao;

	@Override
	protected Class<Metodologia> getManagedClass() {
		return Metodologia.class;
	}

	@Override
	@PostConstruct
	public void inicializar() {
		metodologia = new Metodologia();
		academiaSelecionada = null;
		buscarUsuarioSessao();
		carregarMetodologias();
		buscarAcademias();
	}

	private void buscarUsuarioSessao() {
		usuarioSessao = (Usuario) controleUtil
				.getSessao(EString.NOME_SESSAO_USUARIO.getValue());
	}

	private void carregarMetodologias() {
		try {
			if (loginController.checarPerfilAdmin()) {
				metodologias = metodologiaServico.obterTodos();
			} else {
				metodologias = metodologiaServico.obterTodosPorAcademia(usuarioSessao.getAcademia());
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	private void buscarAcademias() {
		try {
			if (loginController.checarPerfilAdmin()) {
				academias = academiaServico.obterTodos();
			} else {
				academias = new ArrayList<Academia>();
				academias.add(usuarioSessao.getAcademia());
				academiaSelecionada = usuarioSessao.getAcademia();
			}
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String salvar() {
		try {
			if (metodologia.getId() != null) {
				metodologia.setAcademia(academiaSelecionada);
				metodologiaServico.alterar(metodologia);
				MensagemUtil.gerarSucesso("Metodologia.",
						"Alterado com suceso.");
			} else {
				metodologia.setAcademia(academiaSelecionada);
				metodologiaServico.salvar(metodologia);
				MensagemUtil.gerarSucesso("Metodologia.", "Salvo com suceso.");
			}
			setIndexTab(1);
		} catch (BaseServicoException e) {
			MensagemUtil.gerarErro("Metodologia.",
					"Já existe metodologia cadastrada.");
		}
		inicializar();
		return null;
	}

	@Override
	public void deletar(Object obj) {
		Metodologia a = (Metodologia) obj;
		try {
			boolean deletar = metodologiaServico.deletar(a);
			inicializar();
			setIndexTab(1);
			if (deletar) {
				MensagemUtil.gerarSucesso("Metodologia.",
						"Deletado com suceso.");
			} else {
				MensagemUtil.gerarErro("Metodologia.",
						"Existem treinos associados a essa metodologia.");
			}
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Metodologia k) {
		metodologia = k;
		academiaSelecionada = k.getAcademia();
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

	public List<Academia> getAcademias() {
		return academias;
	}

	public void setAcademias(List<Academia> academias) {
		this.academias = academias;
	}

	public Academia getAcademiaSelecionada() {
		return academiaSelecionada;
	}

	public void setAcademiaSelecionada(Academia academiaSelecionada) {
		this.academiaSelecionada = academiaSelecionada;
	}
}
