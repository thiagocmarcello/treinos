package br.com.novotreino.dao;

import java.util.List;

import br.com.novotreino.entidade.Aluno;
import br.com.novotreino.entidade.AlunoTreino;

public class AlunoTreinoDAO extends BaseDAO<AlunoTreino> {

	private static final long serialVersionUID = 1L;

	public AlunoTreinoDAO() {
		super(AlunoTreino.class);
	}

	public void checarAlunoTreinoAtivo() throws BaseDAOException {
		getEm().createNativeQuery("select checar_aluno_treino_ativo();")
		.getResultList();
	}

	public List<Aluno> buscarAlunoTreino() throws BaseDAOException {
		return getEm().createQuery("select distinct(at.aluno) from AlunoTreino at")
				.getResultList();
	}

	public void deletarAlunosTreinos(AlunoTreino at) throws BaseDAOException {
		getEm().createNativeQuery("delete from aluno_treino"
				+ " where _aluno = :_aluno")
				.setParameter("_aluno", at.getAluno().getId())
				.executeUpdate();
	}

}
