package br.com.novotreino.dao;

import java.util.List;

import org.hibernate.envers.internal.entities.mapper.relation.lazy.proxy.SetProxy;

import br.com.novotreino.entidade.Academia;
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
		return getEm().createQuery(
				"select distinct(at.aluno) from AlunoTreino at")
				.getResultList();
	}
	
	public List<Aluno> buscarAlunoTreino(Academia academia) throws BaseDAOException {
		return getEm().createQuery(
				"select distinct(at.aluno) from AlunoTreino at"
				+ " where at.aluno.academia = :_academia")
				.setParameter("_academia", academia)
				.getResultList();
	}

	public void deletarAlunosTreinos(AlunoTreino at) throws BaseDAOException {
		getEm().createNativeQuery(
				"delete from aluno_treino" 
		+ " where _aluno = :_aluno")
				.setParameter("_aluno", at.getAluno().getId()).executeUpdate();
	}
	
	public void ativarInativarAlunosTreinos(boolean ativo, AlunoTreino at) throws BaseDAOException {
		getEm().createNativeQuery(
				"update aluno_treino set ativo = :_ativo where _aluno = :_aluno")
				.setParameter("_ativo", ativo)
				.setParameter("_aluno", at.getAluno().getId())
				.executeUpdate();
	}

	public int verificarSeAlunoTreinoJaExiste(AlunoTreino alunoTreinoExiste) {
		return getEm()
				.createQuery(
						"select at from AlunoTreino at"
								+ " where at.id.aluno = :_idAluno"
								+ " and at.id.treino = :_idTreino")
				.setParameter("_idAluno", alunoTreinoExiste.getId().getAluno())
				.setParameter("_idTreino", alunoTreinoExiste.getId().getTreino())
				.getResultList().size();
	}
}
