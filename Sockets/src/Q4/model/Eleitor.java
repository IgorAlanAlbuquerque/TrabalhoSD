package Q4.model;

public class Eleitor extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Eleitor(String nome, String login, String senha) {
		super(nome,login, senha);
	}

	@Override
	public String toString() {
		return "Eleitor [getNome()=" + getNome() + ", getLogin()=" + getLogin() + ", getSenha()=" + getSenha()
				+ ", isVotou()=" + isVotou() + "]";
	}
	

}
