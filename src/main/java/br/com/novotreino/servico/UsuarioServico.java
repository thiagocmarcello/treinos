package br.com.novotreino.servico;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.novotreino.dao.BaseDAOException;
import br.com.novotreino.dao.UsuarioDAO;
import br.com.novotreino.entidade.Usuario;

@Stateless
public class UsuarioServico extends BaseServico<Usuario> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Override
	@PostConstruct
	protected void inicializar() {
		setDao(usuarioDAO);
	}

	public Usuario autenticar(String login, String senha)
			throws BaseServicoException {
		try {
			return usuarioDAO.autenticar(login, senha);
		} catch (BaseDAOException e) {
			throw new BaseServicoException(e.getMessage());
		}
	}
}
