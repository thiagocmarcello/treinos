package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Aluno;
import br.com.novotreino.entidade.AlunoTreino;
import br.com.novotreino.entidade.Metodologia;
import br.com.novotreino.entidade.Treino;
import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EString;
import br.com.novotreino.servico.AcademiaServico;
import br.com.novotreino.servico.AlunoServico;
import br.com.novotreino.servico.AlunoTreinoServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.MetodologiaServico;
import br.com.novotreino.servico.TreinoServico;
import br.com.novotreino.util.ControleUtil;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class AlunoTreinoController extends BaseController<AlunoTreino>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private AlunoServico alunoServico;
	@EJB
	private TreinoServico treinoServico;
	@EJB
	private MetodologiaServico metodologiaServico;
	@EJB
	private AlunoTreinoServico alunoTreinoServico;
	@EJB
	private AcademiaServico academiaServico;
	@Inject
	private LoginController loginController;
	@Inject
	private ControleUtil controleUtil;

	private Aluno alunoSelecionado;
	private List<Aluno> alunos;

	private Metodologia metodologiaSelecionada;
	private List<Metodologia> metodologias;

	private Treino treinoSelecionado;
	private List<Treino> treinos;

	private List<Treino> treinosCadastro;

	private Date validadeTreino;
	private Integer idAlunoAnterior;
	private boolean editar;

	private List<Academia> academias;
	private Academia academiaSelecionada;
	private Usuario usuarioSessao;
	// private List<AlunoTreino> alunosTreinos;
	private List<Aluno> alunoTreinos;

	@Override
	@PostConstruct
	public void inicializar() {
		editar = false;
		treinosCadastro = new ArrayList<Treino>();
		alunoSelecionado = null;
		validadeTreino = null;
		idAlunoAnterior = 0;
		buscarUsuarioSessao();
		buscarAcademias();
		buscarAlunos();
		buscarMetodologias();
		buscarAlunosTreinos();
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

	private void buscarAlunos() {
		try {
			if (academiaSelecionada != null) {
				alunos = alunoServico
						.obterTodosPorAcademia(academiaSelecionada);
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void buscarTreinos() {
		try {
			if (metodologiaSelecionada != null) {
				treinos = treinoServico
						.obterPorMetodologia(metodologiaSelecionada);
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

	public void buscarAlunosTreinos() {
		try {
			if (loginController.checarPerfilAdmin()) {
				alunoTreinos = alunoTreinoServico.buscarAlunoTreino();
			} else {
				alunoTreinos = alunoTreinoServico
						.buscarAlunoTreino(academiaSelecionada);
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void carregarMetodologiaEAparelho() {
		buscarMetodologias();
		buscarAlunos();
	}

	public void adicionarTreino() {
		inserirTreinoNaLista();
	}

	private void inserirTreinoNaLista() {
		if (treinoSelecionado != null) {
			if (validarTreinoExistente()) {
				treinosCadastro.add(treinoSelecionado);
			}
			treinoSelecionado = null;
		} else {
			MensagemUtil.gerarErro("Aluno Treino.", "Selecione um treino.");
		}
	}

	private boolean validarTreinoExistente() {
		if (treinosCadastro.contains(treinoSelecionado)) {
			MensagemUtil.gerarErro("Aluno Treino.", "Treino já selecionado.");
			return false;
		}
		return true;
	}

	public void removerTreino(Treino t) {
		if (t.getId() != null) {
			treinosCadastro.remove(t);
		}
	}

	@Override
	public String salvar() {
		try {
			if (validarTreinosCadastradosPreenchidos()) {
				// FIXME: Passar para a camada de negocio.
				// É madrugada já e eu não vou refatorar isso pois acordo cedo
				// pra trabalhar!
				// Fica pra depois refatorar essa porquisse =(
				// validarTreinosCadastrados();
				List<AlunoTreino> ats = new ArrayList<AlunoTreino>();
				for (Treino t : treinosCadastro) {
					AlunoTreino at = new AlunoTreino(alunoSelecionado, t);
					at.setDataInicio(new Date());
					at.setDataFim(validadeTreino);
					at.setAtivo(true);
					ats.add(at);
				}
				alunoSelecionado.setAlunosTreinos(ats);
				if (editar) {
					alunoServico.alterar(alunoSelecionado);
					if (alunoSelecionado.getId() == idAlunoAnterior) {
						MensagemUtil.gerarSucesso("Aluno Treino.",
								"Alterado com sucesso.");
					} else {
						MensagemUtil.gerarSucesso("Aluno Treino.",
								"Treino clonado com sucesso.");
					}
				} else {
					alunoServico.alterar(alunoSelecionado);
					MensagemUtil.gerarSucesso("Aluno Treino.",
							"Salvo com sucesso.");
				}
				inicializar();
				setIndexTab(1);
				limparMetodologiaSelecionada();
			} else {
				MensagemUtil
						.gerarErro("Aluno Treino.", "Sem lista de treinos.");
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean validarTreinosCadastradosPreenchidos() {
		if (treinosCadastro.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	private void limparMetodologiaSelecionada() {
		metodologiaSelecionada = null;
	}

	@Override
	public void deletar(Object obj) {
		Aluno aluno = (Aluno) obj;
		List<AlunoTreino> alunosTreinos = aluno.getAlunosTreinos();
		try {
			alunoTreinoServico.deletarAlunosTreinos(alunosTreinos);
			inicializar();
			setIndexTab(1);
			MensagemUtil.gerarSucesso("Aluno Treino.", "Excluido com sucesso.");
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editar(AlunoTreino k) {
		// TODO Auto-generated method stub

	}

	public void editarAluno(Object k) {
		try {
			Aluno aluno = (Aluno) k;
			academiaSelecionada = aluno.getAcademia();
			buscarAlunos();
			idAlunoAnterior = aluno.getId();
			alunoSelecionado = aluno;
			buscarMetodologias();
			//metodologiaSelecionada = academiaSelecionada.get.getMetodologia();
			treinos = new ArrayList<>();
			treinos = treinoServico.obterPorMetodologia(metodologiaSelecionada);
			for (AlunoTreino at : aluno.getAlunosTreinos()) {
				if (treinosCadastro == null) {
					treinosCadastro = new ArrayList<Treino>();
				}
				treinosCadastro.add(at.getTreino());
				validadeTreino = at.getDataFim();
			}
			editar = true;
			setIndexTab(0);
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void ativarInativar(Object obj) {
		AlunoTreino a = (AlunoTreino) obj;
		a.setAtivo(!a.isAtivo());
		try {
			alunoTreinoServico.ativarInativarAlunosTreinos(a);
			setIndexTab(1);
		} catch (BaseServicoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected Class<AlunoTreino> getManagedClass() {
		// TODO Auto-generated method stub
		return null;
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

	public Treino getTreinoSelecionado() {
		return treinoSelecionado;
	}

	public void setTreinoSelecionado(Treino treinoSelecionado) {
		this.treinoSelecionado = treinoSelecionado;
	}

	public List<Treino> getTreinos() {
		return treinos;
	}

	public void setTreinos(List<Treino> treinos) {
		this.treinos = treinos;
	}

	public Metodologia getMetodologiaSelecionada() {
		return metodologiaSelecionada;
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

	public void setMetodologiaSelecionada(Metodologia metodologiaSelecionada) {
		this.metodologiaSelecionada = metodologiaSelecionada;
	}

	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List<Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	public List<Treino> getTreinosCadastro() {
		return treinosCadastro;
	}

	public void setTreinosCadastro(List<Treino> treinosCadastro) {
		this.treinosCadastro = treinosCadastro;
	}

	public Date getValidadeTreino() {
		return validadeTreino;
	}

	public void setValidadeTreino(Date validadeTreino) {
		this.validadeTreino = validadeTreino;
	}

	public List<Aluno> getAlunoTreinos() {
		return alunoTreinos;
	}

	public void setAlunoTreinos(List<Aluno> alunoTreinos) {
		this.alunoTreinos = alunoTreinos;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
}
