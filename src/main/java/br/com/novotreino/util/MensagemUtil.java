package br.com.novotreino.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public final class MensagemUtil {

	private MensagemUtil() {
	}

	public static void gerarErroValidacao(String titulo, String mensagem) {
		FacesMessage msg = new FacesMessage(titulo, mensagem);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(msg);
	}

	public static void gerarSucesso(String titulo, String mensagem) {
		FacesMessage msg = new FacesMessage(titulo, mensagem);
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		gerarFacesMessage(msg);	
	}

	public static void gerarErro(String titulo, String mensagem) {
		FacesMessage msg = new FacesMessage(titulo, mensagem);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		gerarFacesMessage(msg);	
	}
	
	private static void gerarFacesMessage(FacesMessage msg) {
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, msg);
	}
}
