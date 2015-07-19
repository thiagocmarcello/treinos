package br.com.novotreino.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EString;
import br.com.novotreino.interfaces.NavegacaoPagina;
import br.com.novotreino.util.ControleUtil;
import br.com.novotreino.util.PermissaoTelaUtil;

public class AutorizacaoListener implements PhaseListener, NavegacaoPagina, HttpSessionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterPhase(PhaseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		autorizar(event.getFacesContext().getViewRoot().getViewId());

	}

	public void autorizar(String tela) {
		if (tela.equals(LOGIN)) {
			return;
		}
		ControleUtil controleUtil = new ControleUtil();
		Usuario usuario = (Usuario) controleUtil.getSessao(EString.NOME_SESSAO_USUARIO.getValue());
		if (usuario != null) {
			if (PermissaoTelaUtil.temPermissao(usuario.getePerfil(), tela)) {
				return;
			} else {
				controleUtil.redirecionar(LOGIN);
			}
		} else {
			controleUtil.redirecionar(LOGIN);
		}
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
}