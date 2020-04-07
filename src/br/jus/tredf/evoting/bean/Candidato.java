package br.jus.tredf.evoting.bean;

public class Candidato {

	@Override
	public String toString() {
		return "Candidato [nome=" + nome + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Candidato(String nome) {
		super();
		this.nome = nome;
	}
	
	private String nome;
}
