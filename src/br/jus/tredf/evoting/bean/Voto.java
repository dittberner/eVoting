package br.jus.tredf.evoting.bean;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Voto {

	public void incluirCandidato(Candidato c, Byte voto) {
		if (votos == null) {
			votos = new ArrayList<Byte>();
		}
		if (candidatos == null) {
			candidatos = new ArrayList<Candidato>();
		}
		votos.add(voto);
		candidatos.add(c);
	}

	public void encriptar() throws NoSuchAlgorithmException {
		for (int i = 0; i < votos.size(); i++) {
			votosEncriptados.add(votos.get(i));
		}
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < votos.size(); i++) {
			s = s + "[" + candidatos.get(i) + ", " + votos.get(i).byteValue() + "] ";
		}
		return s;
	}

	private ArrayList<Byte> votos;
	private ArrayList<Byte> votosEncriptados;
	private ArrayList<Candidato> candidatos;

}
