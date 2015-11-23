package br.com.novotreino.servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.AlunoTreinoDAO;
import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Aluno;
import br.com.novotreino.entidade.AlunoTreino;
import br.com.novotreino.entidade.Treino;

@Stateless
public class AlunoTreinoServico extends BaseServico<AlunoTreino> {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoTreinoDAO alunoTreinoDAO;

	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(alunoTreinoDAO);
	}

	public void salvarTreinos(List<Treino> treinosCadastro,
			Aluno alunoSelecionado, Date dataFim, boolean editar)
			throws BaseServicoException {
		List<AlunoTreino> ats = new ArrayList<AlunoTreino>();
		for (Treino t : treinosCadastro) {
			AlunoTreino at = new AlunoTreino(alunoSelecionado, t);
			at.setDataInicio(new Date());
			at.setDataFim(dataFim);
			ats.add(at);
		}
		try {
			for (AlunoTreino at : ats) {
				if (editar) {
					alterar(at);
				} else {
					salvar(at);
				}
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void checarValidadeAlunoTreino() throws BaseServicoException {
		alunoTreinoDAO.checarAlunoTreinoAtivo();
	}

	public List<Aluno> buscarAlunoTreino() throws BaseServicoException {
		return alunoTreinoDAO.buscarAlunoTreino();
	}
	
	public List<Aluno> buscarAlunoTreino(Academia academia) throws BaseServicoException {
		return alunoTreinoDAO.buscarAlunoTreino(academia);
	} 

	public void deletarAlunosTreinos(List<AlunoTreino> alunosTreinos)
			throws BaseServicoException {
		for (AlunoTreino at : alunosTreinos) {
			alunoTreinoDAO.deletarAlunosTreinos(at);
		}
	}

	public void ativarInativarAlunosTreinos(AlunoTreino alunoTreino)
			throws BaseServicoException {
			alunoTreinoDAO.ativarInativarAlunosTreinos(alunoTreino.isAtivo(), alunoTreino);
	}
	
	public boolean validarTreinoCadastroJaExiste(AlunoTreino alunoTreinoExiste)
			throws BaseServicoException {
		int resultado = alunoTreinoDAO
				.verificarSeAlunoTreinoJaExiste(alunoTreinoExiste);
		if (resultado > 0) {
			return true;
		} else {
			return false;
		}
	}
}
