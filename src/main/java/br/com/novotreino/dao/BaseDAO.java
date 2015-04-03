package br.com.novotreino.dao;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.stat.Statistics;

/**
 * Classe responsavel por conexao com bando de dados.
 *
 * @author Thiago Marcello.
 * @param <T>
 */
@Local(InterfaceDAO.class)
public abstract class BaseDAO<T> implements InterfaceDAO<T> {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "meutreino")
	private EntityManager entityManager;
	private final Class<T> classe;

	private static Statistics statistics;

	public BaseDAO(Class<T> classe) {
		this.classe = classe;
	}
	
	protected StatelessSession getOpenStatelesSession() {
		return entityManager.unwrap(Session.class).getSessionFactory()
				.openStatelessSession();
	}

	public void getStatistics() {
		if (statistics == null) {
			statistics = entityManager.unwrap(Session.class).getSessionFactory()
					.getStatistics();
			statistics.setStatisticsEnabled(true);
		}
	}

	@Override
	public T salvar(T t) {
		try {
			entityManager.persist(t);
			return t;
		} catch (Exception ex) {
			throw new BaseDAOException(ex.getMessage());
		}
	}

	@Override
	public T alterar(T t) {
		try {
			return entityManager.merge(t);
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public void deletar(T t, Integer id) {
		try {
			entityManager.remove(entityManager.getReference(t.getClass(), id));
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> obterTodos() {
		try {
			return entityManager.createQuery(
					"SELECT obj FROM ".concat(this.classe.getSimpleName())
							.concat(" obj")).getResultList();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public int obterQuantidade() {
		try {
			return ((Long) entityManager.createQuery(
					"SELECT count(obj.id) FROM ".concat(
							this.classe.getSimpleName()).concat(" obj"))
					.getSingleResult()).intValue();
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	@Override
	public T obterPorId(int id) {
		try {
			return (T) entityManager.find(classe, id);
		} catch (Exception e) {
			throw new BaseDAOException(e.getMessage());
		}
	}

	protected boolean registroJaCadastrado(String atributo, Object valor) {
		try {
			if (valor == null) {
				return true;
			}
			getEm().createQuery(
					"SELECT obj FROM " + this.classe.getSimpleName()
							+ " obj WHERE obj." + atributo + " =:_valor")
					.setParameter("_valor", valor).getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	protected EntityManager getEm() {
		return entityManager;
	}
}