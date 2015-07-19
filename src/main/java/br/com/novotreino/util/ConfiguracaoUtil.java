package br.com.novotreino.util;

import br.com.novotreino.enums.EString;

public final class ConfiguracaoUtil {
	
	private ConfiguracaoUtil(){
	}

	public static String emailDeEnvio() {
		return EString.EMAIL.getValue();
	}
	
}
