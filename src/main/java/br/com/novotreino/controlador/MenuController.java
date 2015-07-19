package br.com.novotreino.controlador;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.novotreino.interfaces.NavegacaoPagina;

@Named
@RequestScoped
public class MenuController implements Serializable, NavegacaoPagina {
	
	public String alterarSenha() {
		return ALTERAR_SENHA;
	}

}
