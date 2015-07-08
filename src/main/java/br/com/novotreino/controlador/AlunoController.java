package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Aluno;
import br.com.novotreino.entidade.Cidade;
import br.com.novotreino.entidade.Endereco;
import br.com.novotreino.entidade.Estado;
import br.com.novotreino.servico.AcademiaServico;
import br.com.novotreino.servico.AlunoServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.CidadeServico;
import br.com.novotreino.servico.EstadoServico;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class AlunoController extends BaseController<Aluno> implements
		Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private EstadoServico estadoServico;
	@EJB
	private CidadeServico cidadeServico;
	@EJB
	private AlunoServico alunoServico;
	@EJB
	private AcademiaServico academiaServico;
	/* Componentes de tela */
	private List<SelectItem> estados;
	private Integer estadoSelecionado;
	private List<SelectItem> cidades;
	private Integer cidadeSelecionado;
	private Aluno aluno;
	private Endereco endereco;
	private List<Aluno> alunos;
	private Academia academiaSelecionada;
	private List<Academia> academias;

	@Override
	protected Class<Aluno> getManagedClass() {
		return Aluno.class;
	}

	@Override
	@PostConstruct
	public void inicializar() {
		aluno = new Aluno();
		aluno.setSexo(true);
		aluno.setAtivo(true);
		endereco = new Endereco();
		carregarAlunos();
		carregarAcademias();
		iniciarEstados();

	}

	private void limpar() {
		estadoSelecionado = null;
		cidadeSelecionado = null;
		academiaSelecionada = null;
		inicializar();
	}

	private void carregarAlunos() {
		try {
			alunos = alunoServico.obterTodos();
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void carregarAcademias() {
		try {
			academias = academiaServico.obterTodos();
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
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
	public String salvar() {
		endereco.setCidade(cidadeServico.obterPorId(cidadeSelecionado));
		aluno.setEndereco(endereco);
		aluno.setAcademia(academiaSelecionada);
		try {
			if (aluno.getId() != null) {
				aluno = alunoServico.alterar(aluno);
				MensagemUtil.gerarSucesso("Aluno.", 
						"Alterado com suceso.");
			} else {
				aluno = alunoServico.salvar(aluno);
				MensagemUtil.gerarSucesso("Aluno.", 
						"Salvo com suceso.");
			}
			setIndexTab(1);
		} catch (BaseServicoException e) {
			MensagemUtil.gerarErro("Aluno.", 
					"Email já cadastrado.");
		}
		limpar();
		return null;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public Integer getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Integer estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
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

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	@Override
	public void deletar(Object obj) {
		Aluno a = (Aluno) obj;
		try {
			alunoServico.deletar(a, a.getId());
			limpar();
			setIndexTab(1);
			MensagemUtil.gerarSucesso("Aluno.", 
					"Deletado com suceso.");
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ativarInativar(Object obj) {
		Aluno a = (Aluno) obj;
		aluno = a;
		aluno.setAtivo(!aluno.isAtivo());
		try {
			alunoServico.alterar(aluno);
			limpar();
			setIndexTab(1);
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Aluno k) {
		aluno = k;
		endereco = aluno.getEndereco();
		estadoSelecionado = endereco.getCidade().getEstado().getId();
		cidadeSelecionado = endereco.getCidade().getId();
		academiaSelecionada = aluno.getAcademia();
		carregarCidades();
		setIndexTab(0);
	}

	public Academia getAcademiaSelecionada() {
		return academiaSelecionada;
	}

	public void setAcademiaSelecionada(Academia academiaSelecionada) {
		this.academiaSelecionada = academiaSelecionada;
	}

	public List<Academia> getAcademias() {
		return academias;
	}

	public void setAcademias(List<Academia> academias) {
		this.academias = academias;
	}
}
