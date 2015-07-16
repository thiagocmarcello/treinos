package br.com.novotreino.enums;

public enum EPerfil {
	
	ADMIN("ADMIN"),
	USER("USER");
	
	private String value;
	
	EPerfil(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
