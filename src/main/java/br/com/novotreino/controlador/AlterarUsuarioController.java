package br.com.novotreino.controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.novotreino.email.Email;
import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EString;
import br.com.novotreino.interfaces.NavegacaoPagina;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.UsuarioServico;
import br.com.novotreino.util.ConfiguracaoUtil;
import br.com.novotreino.util.ControleUtil;
import br.com.novotreino.util.CriptografiaUtil;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class AlterarUsuarioController extends BaseController<Usuario> implements
		Serializable, NavegacaoPagina {

	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private Email email;
	@Inject
	private ControleUtil controleUtil;

	private Usuario usuario;

	private String senhaAtual;
	private String novaSenha;
	private String novaSenhaRepita;

	@Override
	@PostConstruct
	public void inicializar() {
		buscarUsuarioSessao();
		inicializarCamposSenhas();
	}

	private void buscarUsuarioSessao() {
		usuario = (Usuario) controleUtil.getSessao(EString.NOME_SESSAO_USUARIO
				.getValue());
	}

	private void inicializarCamposSenhas() {
		senhaAtual = "";
		novaSenha = "";
		novaSenhaRepita = "";
	}

	@Override
	protected Class<Usuario> getManagedClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String salvar() {
		if (checarNovaSenhaConfere() && checarSenhaAtual()) {
			usuario.setSenha(novaSenha);
			try {
				usuarioServico.alterar(usuario);
				email.sendEmail(usuario.getEmail().toLowerCase(), ConfiguracaoUtil.emailDeEnvio(), "Alteração de Senha", 
						"Sua nova senha é: " + novaSenhaRepita);
				inicializarCamposSenhas();
				MensagemUtil.gerarSucesso("Alterar Senha", "Senha alterada.");
			} catch (BaseServicoException e) {
				MensagemUtil.gerarErro("Alterar Senha", "Erro ao alterar.");
				e.printStackTrace();
			}
		} else {
			return null;
		}
		return ALUNO;
	}

	private boolean checarNovaSenhaConfere() {
		if (novaSenha.equals(novaSenhaRepita)) {
			return true;
		} else {
			MensagemUtil.gerarErro("Alterar Senha", "Novas senhas diferentes.");
			return false;
		}
	}
	
	private boolean checarSenhaAtual() {
		String senhaCriptografada = CriptografiaUtil.gerarHashSha1(senhaAtual);
		if (senhaCriptografada.equals(usuario.getSenha())) {
			return true;
		} else {
			MensagemUtil.gerarErro("Alterar Senha", "Senha atual não confere.");
			return false;
		}
	}

	@Override
	public void deletar(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editar(Usuario k) {
		// TODO Auto-generated method stub

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaRepita() {
		return novaSenhaRepita;
	}

	public void setNovaSenhaRepita(String novaSenhaRepita) {
		this.novaSenhaRepita = novaSenhaRepita;
	}
}
