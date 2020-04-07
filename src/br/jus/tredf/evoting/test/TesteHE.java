// Testando Homomorphic encryption
package br.jus.tredf.evoting.test;

import br.jus.tredf.evoting.bean.Candidato;
import br.jus.tredf.evoting.bean.Voto;

public class TesteHE {
	
	public void testar() {
		
		Candidato c1 = new Candidato("Candidato #1");
		Candidato c2 = new Candidato("Candidato #2");
		Candidato c3 = new Candidato("Candidato #3");
		
		Voto cd1 = new Voto();
		cd1.incluirCandidato(c1, new Byte(ZERO));
		cd1.incluirCandidato(c2, new Byte(ZERO));
		cd1.incluirCandidato(c3, new Byte(ZERO));
		
		Voto cd2 = new Voto();
		cd2.incluirCandidato(c1, new Byte(ZERO));
		cd2.incluirCandidato(c2, new Byte(UM));
		cd2.incluirCandidato(c3, new Byte(ZERO));

		Voto cd3 = new Voto();
		cd3.incluirCandidato(c1, new Byte(UM));
		cd3.incluirCandidato(c2, new Byte(ZERO));
		cd3.incluirCandidato(c3, new Byte(UM));
		
		System.out.println(cd1.toString());
		System.out.println(cd2.toString());
		System.out.println(cd3.toString());
	}
	
	private byte ZERO = 0;
	private byte UM = 1;
	private String chave = "asenhaehsecreta";

}
