package br.com.novotreino.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class AlunoTreinoPK implements Serializable {

	@Column(name = "_aluno")
	private int aluno;
	@Column(name = "_treino")
	private int treino;
	
	public AlunoTreinoPK() {
	}
	
	public AlunoTreinoPK(int aluno, int treino) {
		this.aluno = aluno;
		this.treino = treino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + aluno;
		result = prime * result + treino;
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
		AlunoTreinoPK other = (AlunoTreinoPK) obj;
		if (aluno != other.aluno)
			return false;
		if (treino != other.treino)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AlunoTreinoPK [aluno=" + aluno + ", treino=" + treino + "]";
	}
}
