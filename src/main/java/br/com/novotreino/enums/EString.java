package br.com.novotreino.enums;

public enum EString {

	
	NOME_SESSAO_USUARIO("usuario"),
	PAGINA_ALUNO("/paginas/cadastroAluno.xhtml");
	
	private String value;
	
	EString(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
