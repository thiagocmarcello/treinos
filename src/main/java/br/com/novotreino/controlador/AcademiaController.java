package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Cidade;
import br.com.novotreino.entidade.Endereco;
import br.com.novotreino.entidade.Estado;
import br.com.novotreino.servico.AcademiaServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.CidadeServico;
import br.com.novotreino.servico.EstadoServico;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class AcademiaController extends BaseController<Academia> implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private AcademiaServico academiaServico;
	@EJB
	private EstadoServico estadoServico;
	@EJB
	private CidadeServico cidadeServico;
	/* Componentes de tela */
	private Academia academia;
	private List<Academia> academias;
	private Integer estadoSelecionado;
	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private Integer cidadeSelecionado;
	private Endereco endereco;

	/* Beans */

	@Override
	@PostConstruct
	public void inicializar() {
		endereco = new Endereco();
		academia = new Academia();
		carregarAcademias();
		iniciarEstados();
	}

	public void carregarAcademias() {
		try {
			academias = academiaServico.obterTodos();
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void limpar() {
		inicializar();
		estadoSelecionado = null;
		cidadeSelecionado = null;
	}

	@Override
	public String salvar() {
		endereco.setCidade(cidadeServico.obterPorId(cidadeSelecionado));
		academia.setEndereco(endereco);
		try {
			if (academia.getId() != null) {
				academiaServico.alterar(academia);
				MensagemUtil.gerarSucesso("Academia.", "Alterado com suceso.");
			} else {
				academiaServico.salvar(academia);
				MensagemUtil.gerarSucesso("Academia.", "Salvo com suceso.");
			}
			setIndexTab(1);
		} catch (BaseServicoException e) {
			MensagemUtil.gerarErro("Academia.", "Academia já existente.");
		}
		List<Academia> academiass = new ArrayList<Academia>();
		academiass.add(academia);
		limpar();
		return "";
	}

	@Override
	public void deletar(Object obj) {
		Academia a = (Academia) obj;
		try {
			boolean deletado = academiaServico.deletar(a);
			limpar();
			setIndexTab(1);
			if (deletado) {
				MensagemUtil.gerarSucesso("Academia.", "Excluido com suceso.");
			} else {
				MensagemUtil.gerarErro("Academia.",
						"Existem alunos associados a esta academia.");
			}
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Academia k) {
		academia = k;
		endereco = academia.getEndereco();
		estadoSelecionado = endereco.getCidade().getEstado().getId();
		cidadeSelecionado = endereco.getCidade().getId();
		carregarCidades();
		setIndexTab(0);
	}

	private void iniciarEstados() {
		try {
			estados = new ArrayList<SelectItem>();
			for (Estado estado : estadoServico.obterTodos()) {
				estados.add(new SelectItem(estado.getId(), estado.getNome()));
			}
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void carregarCidades() {
		if (estadoSelecionado != null) {
			cidades = new ArrayList<SelectItem>();
			for (Cidade cidade : cidadeServico
					.obterPorEstado(estadoSelecionado)) {
				cidades.add(new SelectItem(cidade.getId(), cidade.getNome()));
			}
		} else {
			cidades = new ArrayList<SelectItem>();
		}
	}

	@Override
	protected Class<Academia> getManagedClass() {
		return Academia.class;
	}

	public Integer getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Integer estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public Integer getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Integer cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}

	public Academia getAcademia() {
		return academia;
	}

	public void setAcademia(Academia academia) {
		this.academia = academia;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Academia> getAcademias() {
		return academias;
	}

	public void setAcademias(List<Academia> academias) {
		this.academias = academias;
	}

}
