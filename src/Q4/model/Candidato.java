package Q4.model;

public class Candidato extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numero;
	public Candidato(String nome, String login, String senha, int numero) {
		super(nome,login,senha);
		this.numero = numero;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Candidato [nome=" + this.getNome() + ", login=" + this.getLogin() + ", votou=" + this.isVotou() + ", numero=" + numero + "]";
	}
	
}
