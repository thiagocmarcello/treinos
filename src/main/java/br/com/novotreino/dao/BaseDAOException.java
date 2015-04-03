package br.com.novotreino.dao;

/**
 * Classe responsavel por tratar excessoes da DAO.
 * 
 * @author Thiago Marcello
 * 
 */
public class BaseDAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseDAOException(String mensagem) {
		super(mensagem);
	}
}
