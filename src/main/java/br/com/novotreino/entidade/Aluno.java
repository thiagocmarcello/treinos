package br.com.novotreino.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings({ "deprecation", "serial" })
@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	@Column
	private Boolean sexo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	private String cpf;
	
	private String observacao;
	
	@Column(unique = true)
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "_academia")
	@ForeignKey(name = "fk_academia")
	private Academia academia;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "_endereco")
	@ForeignKey(name = "fk_endereco")
	private Endereco endereco;
	
	@OneToMany(mappedBy = "aluno", fetch = FetchType.EAGER, 
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AlunoTreino> alunosTreinos;
	
	@Column
	private boolean ativo;
	
	public Aluno() {
	}
	
	public Aluno(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public Academia getAcademia() {
		return academia;
	}

	public void setAcademia(Academia academia) {
		this.academia = academia;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<AlunoTreino> getAlunosTreinos() {
		return alunosTreinos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAlunosTreinos(List<AlunoTreino> alunosTreinos) {
		this.alunosTreinos = alunosTreinos;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getSexo() {
		return sexo;
	}

	public void setSexo(Boolean sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + nome + "]";
	}
}
