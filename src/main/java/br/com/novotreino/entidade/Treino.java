package br.com.novotreino.entidade;

import java.io.Serializable;
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

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings({ "serial", "deprecation" })
@Entity
@Table(name = "treino")
public class Treino implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "_metodologia")
	@ForeignKey(name = "fk_metodologia")
	private Metodologia metodologia;

	@OneToMany(mappedBy = "treino", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Exercicio> exercicios;

	@OneToMany(mappedBy = "treino", fetch = FetchType.EAGER, 
			cascade = CascadeType.REMOVE)
	private List<AlunoTreino> alunosTreinos;
	
	@ManyToOne
	@JoinColumn(name = "_academia")
	@ForeignKey(name = "fk_academia_treino")
	private Academia academia;

	public Treino() {
	}

	public Treino(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Metodologia getMetodologia() {
		return metodologia;
	}

	public void setMetodologia(Metodologia metodologia) {
		this.metodologia = metodologia;
	}

	public List<Exercicio> getExercicios() {
		return exercicios;
	}

	public void setExercicios(List<Exercicio> exercicios) {
		this.exercicios = exercicios;
		if (exercicios != null) {
			for (int i = 0; i < exercicios.size(); i++) {
				exercicios.get(i).setTreino(this);
			}
		}
	}

	public List<AlunoTreino> getAlunosTreinos() {
		return alunosTreinos;
	}

	public void setAlunosTreinos(List<AlunoTreino> alunosTreinos) {
		this.alunosTreinos = alunosTreinos;
	}

	public Academia getAcademia() {
		return academia;
	}

	public void setAcademia(Academia academia) {
		this.academia = academia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Treino other = (Treino) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Treino [id=" + id + "]";
	}
}
