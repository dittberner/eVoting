package br.jus.tredf.evoting.test;

import java.math.BigInteger;
import java.util.ArrayList;

import br.jus.tredf.crypto.paillier.PaillierCrypto;

public class TestePaillier {

	public int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	public void testar() {
		PaillierCrypto pc = new PaillierCrypto();

		ArrayList<BigInteger> votos = new ArrayList<BigInteger>();

		pc.KeyPairGeneration(1024);

//		BigInteger e1 = pc.encrypt(BigInteger.valueOf(33));
//		BigInteger d1 = pc.decrypt(e1);
//		System.out.println(e1);
//		System.out.println(d1);
//
//		//pc.KeyPairGeneration(1024);
//		BigInteger e2 = pc.encrypt(BigInteger.valueOf(45));
//		BigInteger d2 = pc.decrypt(e2);
//		System.out.println(e2);
//		System.out.println(d2);
//		
//		BigInteger e3 = e1.multiply(e2);
//		System.out.println(e3);
//		BigInteger d3 = pc.decrypt(e3);
//		System.out.println(d3);

		long inicio = System.currentTimeMillis();
		int r = 0;
		for (int i = 0; i <= LIMITE; i++) {
			int j = randomWithRange(0, 5);
			votos.add(pc.encrypt(BigInteger.valueOf(j)));
			r += j;
		}
		long fim = System.currentTimeMillis() - inicio;
		System.out.println("Encriptação demorou " + fim);

		BigInteger resultado = votos.get(0);
		for (int i = 1; i <= LIMITE; i++) {
			resultado = resultado.multiply(votos.get(i));
		}
		fim = System.currentTimeMillis() - inicio - fim;
		System.out.println("Decriptação demorou " + fim);

		// System.out.println(resultado);
		System.out.println(pc.decrypt(resultado));
		System.out.println(r);

	}

	private static final int LIMITE = 10000;

}
