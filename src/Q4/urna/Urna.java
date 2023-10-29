package Q4.urna;

import Q4.model.Candidato;
import Q4.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Urna {
	private Map<Candidato, Integer> candidatos;
	private Timer timer;
	private int contador;
	private boolean podeVotar;

	public Urna() {
		candidatos = new HashMap <Candidato, Integer>();
		contador = 60*3; 
		podeVotar = false;
	}
	public void iniVotacao() {
		podeVotar = true;
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
                encerrarVotacao(); // Método a ser chamado após o tempo decorrido
                timer.cancel(); // Cancela o timer após a execução da tarefa
            }
        }, contador * 1000);
	}
	private void encerrarVotacao() {
		podeVotar = false;
		
	}
	public String addCandidato(Candidato candidato) {
		if(!podeVotar) {
			candidatos.put(candidato,0);
			return "candidato adicionado";
		}
		return "nao pode adicionar candidatos durante a votação";
	}
	public String removeCadidato(int numeroCandidato) {
		if(!podeVotar)
			for(Candidato c : candidatos.keySet()) {
				if(c.getNumero()== numeroCandidato) {
					candidatos.remove(c);
					return "candidato removido";
				}
			}
		return "nao pode remover candidatos durante a votação";
	}
	public boolean addVoto(int numeroCandidato) {
		if(podeVotar)
			for(Candidato c : candidatos.keySet()) {
				if(c.getNumero()==numeroCandidato) {
					int votos = candidatos.get(c);
					candidatos.put(c,votos+1);
					return true;
				}
			}
		return false;
	}
	public void result() {
		int totalVotos=0;
		int votosVencedor=-1;
		Candidato vencedor = null;
		for(Candidato c : candidatos.keySet()) {
			totalVotos += candidatos.get(c); //somando todos os votos da eleição
			if(candidatos.get(c) > votosVencedor) {
				votosVencedor = candidatos.get(c); //guardando o candidato que teve mais votos e seu numero de votos
				vencedor = c;
			}
		}
		for (Candidato c : candidatos.keySet()) {
	        int votos = candidatos.get(c);
	        double porcentagem = (votos * 100.0) / totalVotos;
	        System.out.printf("Candidato: "+c.getNome()+", votos: "+votos+", porcentagem: "+porcentagem+"\n");
	    }
		System.out.println("Vencedor: "+vencedor.getNome());
	}
	public Map<Candidato, Integer> getCandidatos() {
		return candidatos;
	}
	public ArrayList<User> listaCandidato(){
		ArrayList<User> candi = new ArrayList<User>();
		for(Candidato c : candidatos.keySet()) {
			candi.add(c);
		}
		return candi;
	}
	public void setCandidatos(Map<Candidato, Integer> candidatos) {
		this.candidatos = candidatos;
	}
	
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public boolean isPodeVotar() {
		return podeVotar;
	}
	public void setPodeVotar(boolean podeVotar) {
		this.podeVotar = podeVotar;
	}
	@Override
	public String toString() {
		return "Urna [candidatos=" + candidatos + "]";
	}
}
