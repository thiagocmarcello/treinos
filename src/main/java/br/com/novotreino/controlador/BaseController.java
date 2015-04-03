package br.com.novotreino.controlador;

import javax.faces.context.FacesContext;

public abstract class BaseController<K> {
	
	private Integer indexTab;
	//@Inject
	private transient FacesContext facesContext;

	protected abstract Class<K> getManagedClass();
	
	public abstract void inicializar();
	
	public abstract String salvar();
	
	public abstract void deletar(Object obj);
	
	public abstract void editar(K k);

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public Integer getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(Integer indexTab) {
		this.indexTab = indexTab;
	}
	
}
