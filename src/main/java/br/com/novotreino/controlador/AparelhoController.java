package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Aparelho;
import br.com.novotreino.servico.AcademiaServico;
import br.com.novotreino.servico.AparelhoServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class AparelhoController extends BaseController<Aparelho> implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private AparelhoServico aparelhoServico;
	@EJB
	private AcademiaServico academiaServico;

	private Aparelho aparelho;
	private List<Aparelho> aparelhos;
	private String nomeAparelho;
	private List<Academia> academias;
	private List<Academia> academiasSelecionadas;

	@Override
	protected Class<Aparelho> getManagedClass() {
		return Aparelho.class;
	}

	@Override
	@PostConstruct
	public void inicializar() {
		aparelho = new Aparelho();
		academiasSelecionadas = new ArrayList<Academia>();
		buscarAparelhos();
		buscarAcademias();
	}

	private void buscarAparelhos() {
		try {
			aparelhos = aparelhoServico.obterTodos();
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buscarAcademias() {
		try {
			academias = academiaServico.obterTodos();
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String salvar() {
		try {
			if ("".equals(aparelho.getId()) || aparelho.getId() == null) {
				aparelho = aparelhoServico.salvar(aparelho);
				MensagemUtil.gerarSucesso("Aparelho.", 
						"Salvo com suceso.");
			} else {
				aparelho = aparelhoServico.alterar(aparelho);
				MensagemUtil.gerarSucesso("Aparelho.", 
						"Alterado com suceso.");
			}
			inicializar();
			setIndexTab(1);
		} catch (BaseServicoException e) {
			MensagemUtil.gerarErro("Aparelho.", 
					"Já existe aparelho cadastrado.");
		}
		return null;
	}

	@Override
	public void deletar(Object obj) {
		Aparelho a = (Aparelho) obj;
		try {
			boolean deletado = aparelhoServico.deletar(a);
			limparCampos();
			buscarAparelhos();
			setIndexTab(1);
			if (deletado) {
				MensagemUtil.gerarSucesso("Aparelho.", 
						"Excluido com suceso.");
				} else {
					MensagemUtil.gerarErro("Aparelho.",
							"Existem treinos associados a este aparelho.");
				}
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Aparelho k) {
		aparelho = k;
		setIndexTab(0);
	}

	private void limparCampos() {
		aparelho = new Aparelho();
	}

	public Aparelho getAparelho() {
		return aparelho;
	}

	public void setAparelho(Aparelho aparelho) {
		this.aparelho = aparelho;
	}

	public String getNomeAparelho() {
		return nomeAparelho;
	}

	public void setNomeAparelho(String nomeAparelho) {
		this.nomeAparelho = nomeAparelho;
	}

	public List<Aparelho> getAparelhos() {
		return aparelhos;
	}

	public void setAparelhos(List<Aparelho> aparelhos) {
		this.aparelhos = aparelhos;
	}

	public List<Academia> getAcademias() {
		return academias;
	}

	public void setAcademias(List<Academia> academias) {
		this.academias = academias;
	}

	public List<Academia> getAcademiasSelecionadas() {
		return academiasSelecionadas;
	}

	public void setAcademiasSelecionadas(List<Academia> academiasSelecionadas) {
		this.academiasSelecionadas = academiasSelecionadas;
	}
}
