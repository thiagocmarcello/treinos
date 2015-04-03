package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.novotreino.entidade.Aluno;
import br.com.novotreino.entidade.AlunoTreino;
import br.com.novotreino.entidade.Metodologia;
import br.com.novotreino.entidade.Treino;
import br.com.novotreino.servico.AlunoServico;
import br.com.novotreino.servico.AlunoTreinoServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.MetodologiaServico;
import br.com.novotreino.servico.TreinoServico;

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

	private Aluno alunoSelecionado;
	private List<Aluno> alunos;

	private Metodologia metodologiaSelecionada;
	private List<Metodologia> metodologias;

	private Treino treinoSelecionado;
	private List<Treino> treinos;

	private List<Treino> treinosCadastro;

	private Date validadeTreino;

	private boolean editar;

	// private List<AlunoTreino> alunosTreinos;
	private List<Aluno> alunoTreinos;

	@Override
	@PostConstruct
	public void inicializar() {
		treinosCadastro = new ArrayList<Treino>();
		buscarAlunos();
		buscarMetodologias();
		buscarAlunosTreinos();
	}

	private void buscarAlunos() {
		try {
			alunos = alunoServico.obterTodos();
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void buscarTreinos() {
		try {
			treinos = treinoServico.obterPorMetodologia(metodologiaSelecionada);
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	private void buscarMetodologias() {
		try {
			metodologias = metodologiaServico.obterTodos();
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void buscarAlunosTreinos() {
		try {
			alunoTreinos = alunoTreinoServico.buscarAlunoTreino();
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void adicionarTreino() {
		treinosCadastro.add(treinoSelecionado);
		treinoSelecionado = new Treino();
	}

	public void removerTreino(Treino t) {
		if (t.getId() != null) {
			treinosCadastro.remove(t);
		}
	}

	@Override
	public String salvar() {
		try {
			// FIXME: Passar para a camada de negocio.
			// É madrugada já e eu não vou refatorar isso pois acordo cedo
			// pra trabalhar!
			// Fica pra depois refatorar essa porquisse =(
			List<AlunoTreino> ats = new ArrayList<AlunoTreino>();
			for (Treino t : treinosCadastro) {
				AlunoTreino at = new AlunoTreino(alunoSelecionado, t);
				at.setDataInicio(new Date());
				at.setDataFim(validadeTreino);
				ats.add(at);
			}
			alunoSelecionado.setAlunosTreinos(ats);
			if (editar) {
				alunoServico.alterar(alunoSelecionado);
			} else {
				alunoServico.salvar(alunoSelecionado);
			}

			inicializar();
			setIndexTab(1);
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
		setIndexTab(1);
		return null;
	}

	@Override
	public void deletar(Object obj) {
		Aluno aluno = (Aluno) obj;
		List<AlunoTreino> alunosTreinos = aluno.getAlunosTreinos();
		try {
			alunoTreinoServico.deletarAlunosTreinos(alunosTreinos);
			inicializar();
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editar(AlunoTreino k) {
		// TODO Auto-generated method stub

	}

	public void editarAluno(Object k) {
		Aluno aluno = (Aluno) k;
		alunoSelecionado = aluno;
		for (AlunoTreino at : aluno.getAlunosTreinos()) {
			if (treinosCadastro == null) {
				treinosCadastro = new ArrayList<Treino>();
			}
			treinosCadastro.add(at.getTreino());
			validadeTreino = at.getDataFim();
		}
		editar = true;
		setIndexTab(0);
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
}
