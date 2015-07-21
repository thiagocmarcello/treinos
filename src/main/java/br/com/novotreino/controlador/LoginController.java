package br.com.novotreino.controlador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EPerfil;
import br.com.novotreino.enums.EString;
import br.com.novotreino.interfaces.NavegacaoPagina;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.UsuarioServico;
import br.com.novotreino.util.ControleUtil;
import br.com.novotreino.util.CriptografiaUtil;
import br.com.novotreino.util.MensagemUtil;
import br.com.novotreino.util.PermissaoTelaUtil;

@Named
@ViewScoped
public class LoginController implements NavegacaoPagina, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private UsuarioServico usuarioServico;
	@Inject
	private ControleUtil controleUtil;
	private String login;
	private String senha;

	public void autenticar() {
		try {
			if (validarCamposPreenchidosLoginESenha()) {
				senha = CriptografiaUtil.gerarHashSha1(senha.toUpperCase());
				Usuario usuario = usuarioServico.autenticar(login, senha);
				 if (usuario != null) {
                     controleUtil.setSessao(EString.NOME_SESSAO_USUARIO.getValue(), usuario);
                     //unicidadeLogin.adicionaUsuarioLogado(usuarioSessao);
                     controleUtil.redirecionar(ALUNO);
                 } else {
                	 MensagemUtil.gerarErro("Login", "Login não encontrado.");
                 }
			}
		} catch (BaseServicoException e) {
			controleUtil.redirecionar(LOGIN);
		}
	}

	private boolean validarCamposPreenchidosLoginESenha() {
		if ((login != null && !login.isEmpty())
				&& (senha != null && !senha.isEmpty())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validarMenu(String tela) {
		Usuario usuarioSessao = (Usuario) controleUtil.getSessao(EString.NOME_SESSAO_USUARIO.getValue());
		if (PermissaoTelaUtil.temPermissao(usuarioSessao.getePerfil(), tela)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void encerrarSessao() {
		controleUtil.limparSessao();
		controleUtil.redirecionar(LOGIN);
	}
	
	public String getNomeUsuarioSessao() {
		return ((Usuario) controleUtil.getSessao(EString.NOME_SESSAO_USUARIO.getValue())).getNome();
	}
	
	public String getPerfilUsuarioSessao() {
		return ((Usuario) controleUtil.getSessao(EString.NOME_SESSAO_USUARIO.getValue())).getePerfil().getValue();
	}
	
	public boolean checarPerfilAdmin() {
		Usuario user = (Usuario) controleUtil.getSessao(EString.NOME_SESSAO_USUARIO.getValue());
		if (user.getePerfil().equals(EPerfil.ADMIN)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
