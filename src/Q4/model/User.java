package Q4.model;


public class User{
	private String nome;
	private String login;
	private String senha;
	private boolean votou = false;

	public User(String nome, String login, String senha) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isVotou() {
		return votou;
	}

	public void setVotou(boolean votou) {
		this.votou = votou;
	}

	@Override
	public String toString() {
		return "User [nome=" + nome + ", login=" + login + ", votou=" + votou + "]";
	}
	
	
}
