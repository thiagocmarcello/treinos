package br.com.novotreino.util;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ControleUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void redirecionar(String urlTela) {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            StringBuilder url = new StringBuilder();
            url.append(fc.getExternalContext().getRequestContextPath());
            url.append(urlTela);
            fc.getExternalContext().redirect(url.toString());
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
	
    public Object getSessao(String sessao) {
        return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
        		.getSession().getAttribute(sessao);
    }
	
    public void setSessao(String chave, Object objeto) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(chave, objeto);
    }
    
    public ControleUtil limparSessao() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
        return this;
    }
}
