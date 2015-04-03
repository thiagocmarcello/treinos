package br.com.novotreino.servico;

import java.io.Serializable;
import java.util.List;

public interface InterfaceServico<T> extends Serializable {

	T salvar(T t) throws BaseServicoException;

	T alterar(T t) throws BaseServicoException;

	void deletar(T t, Integer id) throws BaseServicoException;

	List<T> obterTodos() throws BaseServicoException;

	T obterPorId(Integer id) throws BaseServicoException;

	int obterQuantidade() throws BaseServicoException;

}
