package br.com.novotreino.agendador;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import br.com.novotreino.servico.AlunoTreinoServico;
import br.com.novotreino.servico.BaseServicoException;

@Stateless
public class Tarefas {
	
	@EJB
	private AlunoTreinoServico alunoTreinoServico;
	
	/**
	 * Agendamento para executar Funcao no banco e checar se o treino esta
	 * vencido, se sim, altera coluna ativo para FALSE.
	 */
	@Schedule(hour = "0", minute = "10", dayOfWeek = "*")
	public void calcularChecarAlunoTreino() {
		try {
			alunoTreinoServico.checarValidadeAlunoTreino();
		} catch (BaseServicoException e) {
			e.printStackTrace();
		}
	}
}
