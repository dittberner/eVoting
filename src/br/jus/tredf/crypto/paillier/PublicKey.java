package br.jus.tredf.crypto.paillier;

import java.math.BigInteger;

public class PublicKey {

	public BigInteger getN() {
		return n;
	}

	public BigInteger getG() {
		return g;
	}

	public PublicKey(BigInteger n, BigInteger g) {
		super();
		this.n = n;
		this.g = g;
	}

	private BigInteger n;
	private BigInteger g;

}
