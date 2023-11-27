package Q4.model;

public class Admin extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Admin(String nome, String login, String senha) {
		super(nome,login,senha);
	}
	
	@Override
	public String toString() {
		return "Admin [getNome()=" + getNome() + ", getLogin()=" + getLogin() + ", getSenha()=" + getSenha()
				+ ", isVotou()=" + isVotou() + "]";
	}
	

}
