package br.com.novotreino.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CriptografiaUtil {

	private CriptografiaUtil() {
	}

	public static String gerarHashSha1(String conteudo) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(conteudo.getBytes());
			byte[] bytes = messageDigest.digest();
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
				int parteBaixa = bytes[i] & 0xf;
				if (parteAlta == 0) {
					s.append('0');
				}
				s.append(Integer.toHexString(parteAlta | parteBaixa));
			}
			return s.toString().toUpperCase();
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			return null;
		}
	}
}
