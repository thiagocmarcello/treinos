package br.com.novotreino.dao;

import javax.persistence.NoResultException;

import br.com.novotreino.entidade.Usuario;

public class UsuarioDAO extends BaseDAO<Usuario> {

	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(Usuario.class);
	}

	public Usuario autenticar(String login, String senha) throws BaseDAOException {
		try {
			Usuario usuario = (Usuario) getEm()
					.createQuery(
							"select u from Usuario u"
									+ " where u.login = :_login"
									+ " and u.senha = :_senha"
									+ " and u.ativo = true")
					.setParameter("_login", login)
					.setParameter("_senha", senha)
					.getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			return null;
		}
	}
}
