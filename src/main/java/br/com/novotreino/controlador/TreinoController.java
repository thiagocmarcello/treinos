package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Aluno;
import br.com.novotreino.entidade.Aparelho;
import br.com.novotreino.entidade.Exercicio;
import br.com.novotreino.entidade.Metodologia;
import br.com.novotreino.entidade.Treino;
import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EString;
import br.com.novotreino.servico.AcademiaServico;
import br.com.novotreino.servico.AparelhoServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.MetodologiaServico;
import br.com.novotreino.servico.TreinoServico;
import br.com.novotreino.util.ControleUtil;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class TreinoController extends BaseController<Treino> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TreinoServico treinoServico;
	@EJB
	private AparelhoServico aparelhoServico;
	@EJB
	private MetodologiaServico metodologiaServico;
	@EJB
	private AcademiaServico academiaServico;
	@Inject
	private LoginController loginController;
	@Inject
	private ControleUtil controleUtil;

	private Treino treino;
	private List<Treino> treinos;
	private Exercicio exercicio;
	private List<Exercicio> exercicios;
	private Aparelho aparelho;
	private List<Aparelho> aparelhos;
	private Aparelho aparelhoSelecionado;
	private Aluno alunoSelecionado;
	private List<Aluno> alunos;
	private Metodologia metodologiaSelecionada;
	private List<Metodologia> metodologias;

	private List<Academia> academias;
	private Academia academiaSelecionada;
	private Usuario usuarioSessao;

	@Override
	@PostConstruct
	public void inicializar() {
		treino = new Treino();
		treinos = new ArrayList<Treino>();
		exercicio = new Exercicio();
		exercicios = new ArrayList<Exercicio>();
		metodologiaSelecionada = new Metodologia();
		buscarUsuarioSessao();
		buscarAcademias();
		buscarTreinos();
		buscarAparelhos();
		buscarMetodologias();
	}

	private void buscarUsuarioSessao() {
		usuarioSessao = (Usuario) controleUtil
				.getSessao(EString.NOME_SESSAO_USUARIO.getValue());
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
			e.printStackTrace();
		}
	}

	private void buscarMetodologias() {
		try {
			if (academiaSelecionada != null) {
				metodologias = metodologiaServico
						.obterTodosPorAcademia(academiaSelecionada);
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	private void buscarAparelhos() {
		try {
			if (academiaSelecionada != null) {
				aparelhos = aparelhoServico
						.obterTodosPorAcademia(academiaSelecionada);
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	private void buscarTreinos() {
		try {
			if (loginController.checarPerfilAdmin()) {
				treinos = treinoServico.obterTodos();
			} else {
				treinos = treinoServico.obterTodosPorAcademia(usuarioSessao.getAcademia());
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void carregarMetodologiaEAparelho() {
		buscarMetodologias();
		buscarAparelhos();
	}

	public void adicionarExercicio() {
		if (aparelhoSelecionado != null) {
			exercicio.setAparelho(aparelhoSelecionado);
			exercicios.add(exercicio);
			setIndexTab(0);
			limparCampos();
		} else {
			MensagemUtil.gerarErro("Treino.", "Selecione o aparelho.");
		}
	}

	public void removerExercicio(Exercicio ex) {
		exercicios.remove(ex);
	}

	private void limparCampos() {
		exercicio = new Exercicio();
		aparelhoSelecionado = new Aparelho();
	}

	@Override
	public String salvar() {
		if (validarTreino()) {
			treino.setAcademia(academiaSelecionada);
			treino.setExercicios(exercicios);
			treino.setMetodologia(metodologiaSelecionada);
			try {
				if (treino.getId() == null) {
					treinoServico.salvar(treino);
					MensagemUtil.gerarSucesso("Treino.", "Salvo com suceso.");
				} else {
					treinoServico.alterar(treino);
					setIndexTab(1);
					MensagemUtil
							.gerarSucesso("Treino.", "Alterado com suceso.");
				}
			} catch (ValidatorException e) {
			} catch (BaseServicoException e) {
				e.printStackTrace();
			}
			inicializar();
			setIndexTab(1);
		} else {
			MensagemUtil.gerarErro("Treino.", "Treino sem exercicios.");
		}
		return null;
	}

	private boolean validarTreino() throws ValidatorException {
		if (exercicios == null || exercicios.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void deletar(Object obj) {
		Treino t = (Treino) obj;
		try {
			boolean deletado = treinoServico.deletar(t);
			if (deletado) {
				inicializar();
				MensagemUtil.gerarSucesso("Treino.", "Excluido com suceso.");
			} else {
				MensagemUtil.gerarErro("Treino.",
						"Este treino esta sendo ultilizado por Aluno.");
			}
			setIndexTab(1);
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Treino k) {
		treino = k;
		academiaSelecionada = k.getAcademia();
		buscarAparelhos();
		buscarMetodologias();
		buscarTreinos();
		exercicios = treino.getExercicios();
		metodologiaSelecionada = treino.getMetodologia();
		setIndexTab(0);
	}

	@Override
	protected Class<Treino> getManagedClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}

	public List<Treino> getTreinos() {
		return treinos;
	}

	public void setTreinos(List<Treino> treinos) {
		this.treinos = treinos;
	}

	public Exercicio getExercicio() {
		return exercicio;
	}

	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}

	public List<Exercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<Exercicio> exercicios) {
		this.exercicios = exercicios;
	}

	public Aparelho getAparelho() {
		return aparelho;
	}

	public void setAparelho(Aparelho aparelho) {
		this.aparelho = aparelho;
	}

	public List<Aparelho> getAparelhos() {
		return aparelhos;
	}

	public void setAparelhos(List<Aparelho> aparelhos) {
		this.aparelhos = aparelhos;
	}

	public Aparelho getAparelhoSelecionado() {
		return aparelhoSelecionado;
	}

	public void setAparelhoSelecionado(Aparelho aparelhoSelecionado) {
		this.aparelhoSelecionado = aparelhoSelecionado;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public Metodologia getMetodologiaSelecionada() {
		return metodologiaSelecionada;
	}

	public void setMetodologiaSelecionada(Metodologia metodologiaSelecionada) {
		this.metodologiaSelecionada = metodologiaSelecionada;
	}

	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List<Metodologia> metodologias) {
		this.metodologias = metodologias;
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