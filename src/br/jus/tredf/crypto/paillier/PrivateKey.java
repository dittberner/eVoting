package br.jus.tredf.crypto.paillier;

import java.math.BigInteger;

public class PrivateKey {

	public BigInteger getMu() {
		return mu;
	}

	public BigInteger getLambda() {
		return lambda;
	}

	public PrivateKey(BigInteger mu, BigInteger lambda) {
		super();
		this.mu = mu;
		this.lambda = lambda;
	}

	private BigInteger mu;
	private BigInteger lambda;
}
