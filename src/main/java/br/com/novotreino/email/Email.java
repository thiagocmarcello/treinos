package br.com.novotreino.email;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class Email {

	@Resource(name = "java:jboss/mail/meutreino")
	private Session mailSession;

	@Asynchronous
	public void sendEmail(String to, String from, String subject, String content) {
		try {
			// Criação de uma mensagem simples
			Message message = new MimeMessage(mailSession);
			// Cabeçalho do Email
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			// Corpo do email
			message.setText(content);
			// Envio da mensagem
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
