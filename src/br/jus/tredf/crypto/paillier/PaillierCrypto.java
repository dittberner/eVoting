package br.jus.tredf.crypto.paillier;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PaillierCrypto {

	/**
	 * Key generation 
	 * 1. Choose two large prime numbers p and q randomly and
	 * independently of each other such that gcd(pq, (p-1)(q-1)) = 1 (primos
	 * relativos um ao outro). This property is assured if both primes are of equal
	 * length. 
	 * 2. Compute n = pq and lambda = lcm(p-1, q-1). lcm means Least Common
	 * Multiple. 
	 * 3. Select random integer g where g in Z*n^2 4. Ensure n divides the
	 * order of g by checking the existence of the following modular multiplicative
	 * inverse: mu = (L(g^lambda mod n^2))^-1 mod n, where function L is defined as
	 * L(x) = (x-1)/n.
	 * 
	 * The public (encryption) key is (n, g). The private (decryption) key is
	 * (lambda, mu ).
	 */
	private BigInteger p;
	private BigInteger q;
	private BigInteger lambda;
	private BigInteger mu;

	private BigInteger n;
	private BigInteger nSquare;

	private BigInteger g;

	SecureRandom sr;

	/*
	 * Número de bits do módulo
	 */
	private int modBitLength = 1024;

	/**
	 * getStrongPrime generates a strong prime as following: The strong prime p will
	 * be such that p+1 has a large prime factor s p-1 will have a large prime
	 * factor r. p+1 has a large prime factor s. r-1 has a large prime factor t. so
	 * r is the first prime in the sequence 2t+1, 2*2t+1, 2*3t+1, The strong prime p
	 * is the first prime in the sequence 2rs+p*, 2*2rs+p*, 2*3rs+p*,...
	 *
	 * @return a BigIntger strong prime
	 * @author:David Bishop URL: <a href=
	 *               "https://books.google.com.my/books/about/Introduction_to_Cryptography_with_Java_A.html?id=yxPnt4S3mFMC&redir_esc=y">https://books.google.com.my/books/about/Introduction</a><br>
	 */
	public BigInteger getStrongPrime() {

		this.sr = new SecureRandom();

		// The strong prime p will be such that p + 1 has a large prime factor s
		BigInteger s = new BigInteger(this.modBitLength / 2 - 8, this.modBitLength, this.sr);

		// t will be a large prime factor of r, which follows
		BigInteger t = new BigInteger(this.modBitLength / 2 - 8, this.modBitLength, this.sr);

		BigInteger i = BigInteger.valueOf(1);

		// p-1 will have a large prime factor
		// r is the first prime in the sequence 2t+1, 2*2t+1, 2*3t+1,...
		BigInteger r;
		BigInteger _TWO = BigInteger.valueOf(2);

		do {
			r = _TWO.multiply(i).multiply(t).add(BigInteger.ONE);
			i = i.add(BigInteger.ONE);
		} while (!r.isProbablePrime(this.modBitLength));

		BigInteger z = s.modPow(r.subtract(_TWO), r);// modular inverse
		BigInteger pstar = _TWO.multiply(z).multiply(s).subtract(BigInteger.ONE);
		BigInteger k = BigInteger.valueOf(1);

		// The strong prime p is the first prime in the sequence 2rs+p*, 2*2rs+p*,
		// 2*3rs+p*,...
		BigInteger _p = _TWO.multiply(r).multiply(s).add(pstar);
		while (_p.bitLength() <= this.modBitLength) {
			k = k.multiply(_TWO);
			_p = _TWO.multiply(k).multiply(r).multiply(s).add(pstar);
		}

		while (!_p.isProbablePrime(this.modBitLength)) {
			k = k.add(BigInteger.ONE);
			_p = _TWO.multiply(k).multiply(r).multiply(s).add(pstar);
		}

		return _p;
	}

	/**
	 * randomNumFromZstarnNsquare requires no input parameter(s). the function uses
	 * secureRandom class this function checks whether the random number generated
	 * belongs to z*{n}
	 *
	 * @return a random BigIntger r that belongs in Z*_{n}
	 */
	public BigInteger randomNumFromZN() {
		BigInteger r;

		do {
			r = new BigInteger(this.modBitLength, new SecureRandom());
		} while (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(this.n) >= 0); // r belongs to z*{n}

		return r;
	}

	/**
	 * randomNumFromZstarnNsquare requires no input parameter(s). the function uses
	 * secureRandom class It also checks whether the random number generated is not
	 * equal to nsquare and and It checks that r does not have common factor with
	 * nsquare.
	 *
	 * @return a random BigIntger r which belongs in Z*_{n^2}
	 */
	public BigInteger randomNumFromZStarNSquare() {
		BigInteger r;
		do {
			r = new BigInteger(this.modBitLength * 2, new SecureRandom());
		} while (r.compareTo(this.nSquare) >= 0 || r.gcd(this.nSquare).intValue() != 1); // check if g is relative prime
																							// to nSquared
		return r;
	}

	/**
	 * generates PaillierKeyPair object which holds public key and private key.
	 * rejects bit length which is less than 1024 bits generates strong prime p and
	 * q, set values for n, nsquare,g,u and lambda verifies g, by computing
	 * gcd(L(g^lambda mod n^2), n) = 1, where L(u) = (u-1)/n *
	 * 
	 * @param modBitLenVal number of bits of modulus.
	 * @param certainty    probability that the new BigInteger represents a prime
	 *                     number will exceed (1 - 2^(-certainty)). affect execution
	 *                     time generates public key and private key.
	 * @return PaillierKeyPair object which holds public key and private key.
	 */
	public void KeyPairGeneration(int modBitLenVal) {

		this.modBitLength = (modBitLenVal > 1024) ? modBitLenVal : 1024;

		this.p = getStrongPrime();
		do {
			this.q = getStrongPrime();
		} while (this.q.compareTo(p) == 0); // Garantir que os dois números primos são diferentes

		this.n = this.p.multiply(this.q);
		this.nSquare = this.n.multiply(this.n);

		// lambda =(p-1)(q-1)/(gcd(p-1),(q-1))
		this.lambda = this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE))
				.divide(this.p.subtract(BigInteger.ONE).gcd(this.q.subtract(BigInteger.ONE)));

		/* check whether g is good. ie. gcd L(g^lambda mod n^2), n ) = 1 */
		do {
			this.g = randomNumFromZStarNSquare();
		} // verify g, by computing gcd(L(g^lambda mod n^2), n) = 1, where L(u) = (u-1)/n
		while (this.g.modPow(this.lambda, this.nSquare).subtract(BigInteger.ONE).divide(this.n).gcd(this.n)
				.intValue() != 1);

		this.mu = this.g.modPow(lambda, this.nSquare).subtract(BigInteger.ONE).divide(this.n).modInverse(this.n);

		PublicKey publicKey = new PublicKey(n, g);
		PrivateKey privateKey = new PrivateKey(lambda, mu);

		// PaillierKeyPair keyPair = new PaillierKeyPair(publicKey, privateKey);
	}

	public BigInteger encrypt(BigInteger message) {

		// PublicKey pubKey = readPublicKeyFromFile(fileName);
		// g = this.g;
		// n = this.n;

		BigInteger r = randomNumFromZN();
		return g.modPow(message, nSquare).multiply(r.modPow(n, nSquare)).mod(nSquare);
	}

	/**
	 * Decrypts ciphertext c. plaintext m = L(c^lambda mod n^2) * u mod n, where u =
	 * (L(g^lambda mod n^2))^(-1) mod n.
	 *
	 * @param ciphertext ciphertext as a BigInteger
	 * @param fileName   which contains the name of the file that contain the
	 *                   private key the private key is retrieved from the file with
	 *                   the function readPrivateKeyFromFile
	 * @return plaintext as a Byte array
	 * @throws java.io.IOException in case there is failure to read/write the key
	 *         from the file.
	 */
	public BigInteger decrypt(BigInteger ciphertext) {

//		PrivateKey privKey = readPrivateKeyFromFile(fileName);
//		lambda = privKey.get_lambda();
//		u = privKey.get_u();
		return ciphertext.modPow(this.lambda, this.nSquare).subtract(BigInteger.ONE).divide(this.n).multiply(this.mu).mod(this.n);
	}

}
