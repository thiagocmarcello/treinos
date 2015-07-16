package br.com.novotreino.util;

import java.util.ArrayList;
import java.util.List;

import br.com.novotreino.enums.EPerfil;
import br.com.novotreino.interfaces.NavegacaoPagina;

public final class PermissaoTelaUtil implements NavegacaoPagina {

	private static List<String> paginasUser = new ArrayList<String>();

	private PermissaoTelaUtil() {
	}

	static {
		paginasUser.add(ALUNO);
		paginasUser.add(ALUNO_TREINO);
		paginasUser.add(APARELHO);
		paginasUser.add(METODOLOGIA);
		paginasUser.add(TREINO);
	}

	public static boolean temPermissao(EPerfil ePerfil, String tela) {
		if (ePerfil.equals(EPerfil.ADMIN)) {
			return true;
		} else if (paginasUser.contains(tela)) {
			return true;
		} else {
			return false;
		}
	}
}
