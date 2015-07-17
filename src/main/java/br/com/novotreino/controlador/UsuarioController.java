package br.com.novotreino.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.novotreino.entidade.Academia;
import br.com.novotreino.entidade.Cidade;
import br.com.novotreino.entidade.Endereco;
import br.com.novotreino.entidade.Estado;
import br.com.novotreino.entidade.Usuario;
import br.com.novotreino.enums.EPerfil;
import br.com.novotreino.servico.AcademiaServico;
import br.com.novotreino.servico.BaseServicoException;
import br.com.novotreino.servico.CidadeServico;
import br.com.novotreino.servico.EstadoServico;
import br.com.novotreino.servico.UsuarioServico;
import br.com.novotreino.util.MensagemUtil;

@Named
@ViewScoped
public class UsuarioController extends BaseController<Usuario> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private AcademiaServico academiaServico;
	@EJB
	private UsuarioServico usuarioServico;
	@EJB
	private EstadoServico estadoServico;
	@EJB
	private CidadeServico cidadeServico;

	private String senhaRepita;
	private Usuario usuario;
	private Endereco enderecoUsuario;
	private List<Academia> academias;
	private Academia academiaSelecionada;
	private EPerfil ePerfil;
	private EPerfil ePerfilSelecionado;
	private List<SelectItem> estados;
	private Integer estadoSelecionado;
	private List<SelectItem> cidades;
	private Integer cidadeSelecionado;

	@Override
	@PostConstruct
	public void inicializar() {
		inicializarVariaveis();
		buscarAcademias();
		iniciarEstados();
	}

	private void inicializarVariaveis() {
		usuario = new Usuario();
		enderecoUsuario = new Endereco();
		academias = new ArrayList<Academia>();
	}

	private void buscarAcademias() {
		try {
			academias = academiaServico.obterTodos();
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	private void iniciarEstados() {
		try {
			estados = new ArrayList<SelectItem>();
			for (Estado estado : estadoServico.obterTodos()) {
				estados.add(new SelectItem(estado.getId(), estado.getNome()));
			}
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}

	public void carregarCidades() {
		if (estadoSelecionado != null) {
			cidades = new ArrayList<SelectItem>();
			for (Cidade cidade : cidadeServico
					.obterPorEstado(estadoSelecionado)) {
				cidades.add(new SelectItem(cidade.getId(), cidade.getNome()));
			}
		} else {
			cidades = new ArrayList<SelectItem>();
		}
	}

	@Override
	public String salvar() {
		if (verificarSenhas()) {
			enderecoUsuario.setCidade(buscarCidade());
			usuario.setEndereco(enderecoUsuario);
			usuario.setAcademia(academiaSelecionada);
			usuario.setePerfil(ePerfilSelecionado);
			usuario.setAtivo(true);
			try {
				usuario = usuarioServico.salvar(usuario);
				if (usuario.getId() != null) {
					MensagemUtil.gerarSucesso("Usuario.",
							"Usuario salvo com sucesso.");
					setIndexTab(1);
					limparCampos();
				} else {
					MensagemUtil.gerarErro("Usuario.", "Usuario não salvo.");
				}
			} catch (BaseServicoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			MensagemUtil.gerarErro("Usuario.", "Senhas não conferem.");
		}
		return null;
	}

	private boolean verificarSenhas() {
		if (usuario.getSenha().equals(senhaRepita)) {
			return true;
		} else {
			return false;
		}
	}

	private Cidade buscarCidade() {
		return cidadeServico.obterPorId(cidadeSelecionado);
	}
	
	private void limparCampos() {
		estadoSelecionado = null;
		cidadeSelecionado = null;
		academiaSelecionada = null;
		inicializar();
	}

	@Override
	protected Class<Usuario> getManagedClass() {
		// TODO Auto-generated method stub
		return null;
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

	public List<Academia> getAcademias() {
		return academias;
	}

	public void setAcademias(List<Academia> academias) {
		this.academias = academias;
	}

	public String getSenhaRepita() {
		return senhaRepita;
	}

	public void setSenhaRepita(String senhaRepita) {
		this.senhaRepita = senhaRepita;
	}

	public Endereco getEnderecoUsuario() {
		return enderecoUsuario;
	}

	public void setEnderecoUsuario(Endereco enderecoUsuario) {
		this.enderecoUsuario = enderecoUsuario;
	}

	public EPerfil getePerfil() {
		return ePerfil;
	}

	public void setePerfil(EPerfil ePerfil) {
		this.ePerfil = ePerfil;
	}

	public EPerfil getePerfilSelecionado() {
		return ePerfilSelecionado;
	}

	public void setePerfilSelecionado(EPerfil ePerfilSelecionado) {
		this.ePerfilSelecionado = ePerfilSelecionado;
	}

	public EPerfil[] getPerfil() {
		return EPerfil.values();
	}

	public Academia getAcademiaSelecionada() {
		return academiaSelecionada;
	}

	public void setAcademiaSelecionada(Academia academiaSelecionada) {
		this.academiaSelecionada = academiaSelecionada;
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public Integer getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Integer estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public Integer getCidadeSelecionado() {
		return cidadeSelecionado;
	}

	public void setCidadeSelecionado(Integer cidadeSelecionado) {
		this.cidadeSelecionado = cidadeSelecionado;
	}
}
