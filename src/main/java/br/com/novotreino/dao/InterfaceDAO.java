package br.com.novotreino.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface responsavel por conter metodos que devem ser implementados
 * nas classes DAO.
 * 
 * @author Thiago Marcello.
 *
 * @param <T>
 */
public interface InterfaceDAO<T> extends Serializable {

	T salvar(final T t);

	T alterar(final T t);

	void deletar(final T t, Integer id);

	List<T> obterTodos();

	int obterQuantidade();

	T obterPorId(final int id);
}
