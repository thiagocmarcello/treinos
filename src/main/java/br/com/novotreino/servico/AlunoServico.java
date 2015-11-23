package br.com.novotreino.servico;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.AlunoDAO;
import br.com.novotreino.dao.BaseDAOException;
import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Aluno;
import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EPerfil;

@Stateless
public class AlunoServico extends BaseServico<Aluno> {

	private static final long serialVersionUID = 1L;
	@Inject
	private AlunoDAO alunoDAO;

	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(alunoDAO);
	}

	public List<Aluno> obterTodosPorAcademia(Usuario usuario)
			throws BaseServicoException {
		try {
			if (usuario.getePerfil().equals(EPerfil.ADMIN)) {
				return alunoDAO.obterTodos();
			} else {
				return alunoDAO.obterTodosPorAcademia(usuario.getAcademia());
			}
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}

	public List<Aluno> obterTodosPorAcademia(Academia academiaSelecionada) throws BaseServicoException {
		return alunoDAO.obterTodosPorAcademia(academiaSelecionada);
	}
}
