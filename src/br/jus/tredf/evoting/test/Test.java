package br.jus.tredf.evoting.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.jus.tredf.crypto.paillier.PaillierCrypto;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Paillier Crypto");
		try {
			testar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	public static void testar() throws IOException {
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
		
		String nomeArquivo = "enc-" + new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());

		File fEncriptacao = new File(nomeArquivo);
		fEncriptacao.createNewFile();
		FileWriter fw = new FileWriter(fEncriptacao);
		PrintWriter pw = new PrintWriter(fw);

		int r = 0; // resultado da soma, para conferir com o resultado da decriptação
		
		for (int i = 0; i <= LIMITE; i++) {
			
			System.out.print("[");
			int pb = 0;
			for (pb = 0; pb < (30*i/LIMITE); pb++) {
				System.out.print(".");
			}
			for (int espaco = pb; espaco < 30; espaco++) {
				System.out.print(" ");
			}
			System.out.print("]\r");
			int j = randomWithRange(0, 1);

			long inicioEncriptacao = System.currentTimeMillis();
			votos.add(pc.encrypt(BigInteger.valueOf(j)));
			long fimEncriptacaco = System.currentTimeMillis() - inicioEncriptacao;
			
			pw.printf("%d;%d%n", i, fimEncriptacaco);

			r += j;
		}
		pw.close();
		fw.close();
		
		
		nomeArquivo = "dec-" + new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());

		File fDecriptacao = new File(nomeArquivo);
		fEncriptacao.createNewFile();
		fw = new FileWriter(fDecriptacao);
		pw = new PrintWriter(fw);
		

		BigInteger resultado = votos.get(0);
		for (int i = 1; i <= LIMITE; i++) {
			System.out.print("[");
			int pb = 0;
			for (pb = 0; pb < (30*i/LIMITE); pb++) {
				System.out.print(".");
			}
			for (int espaco = pb; espaco < 30; espaco++) {
				System.out.print(" ");
			}
			System.out.print("]\r");
			
			pw.printf("%d;%d;", i - 1, resultado.toString().length());
			long inicioMultiplicacao = System.currentTimeMillis();
			resultado = resultado.multiply(votos.get(i));
			long fimMultiplicacao = System.currentTimeMillis() - inicioMultiplicacao;
			pw.printf("%d;%d%n", votos.get(i).toString().length(), fimMultiplicacao);
		}
		pw.close();
		fw.close();
		
		// System.out.println(resultado);
		System.out.println(pc.decrypt(resultado));
		System.out.println(r);

	}

	private static final int LIMITE = 1000;

}
