package Q4.urna;

import Q4.model.Candidato;
import java.util.HashMap;
import java.util.Map;

public class Urna {
	private Map<Candidato, Integer> candidatos;

	public Urna() {
		candidatos = new HashMap <Candidato, Integer>();
	}
	public void addCandidato(Candidato candidato) {
		candidatos.put(candidato,0);
	}
	public void removeCadidato(int numeroCandidato) {
		for(Candidato c : candidatos.keySet()) {
			if(c.getNumero()== numeroCandidato) {
				candidatos.remove(c);
				return;
			}
		}
	}
	public void addVoto(int numeroCandidato) {
		for(Candidato c : candidatos.keySet()) {
			if(c.getNumero()==numeroCandidato) {
				int votos = candidatos.get(c);
				candidatos.put(c,votos+1);
				return;
			}
		}
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
	public void setCandidatos(Map<Candidato, Integer> candidatos) {
		this.candidatos = candidatos;
	}
	@Override
	public String toString() {
		return "Urna [candidatos=" + candidatos + "]";
	}
}
