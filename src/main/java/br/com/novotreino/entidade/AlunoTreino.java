package br.com.novotreino.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "aluno_treino")
public class AlunoTreino implements Serializable {

	@EmbeddedId
	private AlunoTreinoPK id;
	
	@ManyToOne
	@JoinColumn(name = "_aluno" , insertable = false, updatable = false)
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name = "_treino", insertable = false, updatable = false)
	private Treino treino;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_inicio")
	private Date dataInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_fim")
	private Date dataFim;
	
	@Column
	private boolean ativo;
	
	public AlunoTreino() {
	} 
	
	public AlunoTreino(Aluno aluno, Treino treino) {
		this.id = new AlunoTreinoPK(aluno.getId(), treino.getId());
		this.aluno = aluno;
		this.treino = treino;
	}

	@PrePersist
	public void ativarTrieno() {
		this.ativo = true;
	}
	
	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}

	public AlunoTreinoPK getId() {
		return id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
