package cn.edu.hit.cs.zhycryptdemo.common;

/**
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

import java.math.BigInteger;
import java.util.Random;

/**
 * PaillierUtils Cryptosystem <br>
 * <br>
 * References: <br>
 * [1] Pascal PaillierUtils,
 * "Public-Key Cryptosystems Based on Composite Degree Residuosity Classes,"
 * EUROCRYPT'99. URL:
 * <a href="http://www.gemplus.com/smart/rd/publications/pdf/Pai99pai.pdf">http:
 * //www.gemplus.com/smart/rd/publications/pdf/Pai99pai.pdf</a><br>
 *
 * [2] PaillierUtils cryptosystem from Wikipedia. URL:
 * <a href="http://en.wikipedia.org/wiki/Paillier_cryptosystem">http://en.
 * wikipedia.org/wiki/Paillier_cryptosystem</a>
 *
 * @author Kun Liu (kunliu1@cs.umbc.edu)
 * @version 1.0
 */
public class PaillierUtils {

    private static PaillierUtils paillierUtils;

    /**
     * p and q are two large primes. lambda = lcm(p-1, q-1) =
     * (p-1)*(q-1)/gcd(p-1, q-1).
     */
    public BigInteger p, q, lambda;
    /**
     * n = p*q, where p and q are two large primes.
     */
    public BigInteger n;
    /**
     * nsquare = n*n
     */
    public BigInteger nsquare;
    /**
     * a random integer in Z*_{n^2} where gcd (L(g^lambda mod n^2), n) = 1.
     */
    private BigInteger g;
    /**
     * number of bits of modulus
     */
    private int bitLength;

    /**
     * Constructs an instance of the PaillierUtils cryptosystem.
     *
     * @param bitLengthVal
     *            number of bits of modulus
     * @param certainty
     *            The probability that the new BigInteger represents a prime
     *            number will exceed (1 - 2^(-certainty)). The execution time of
     *            this constructor is proportional to the value of this
     *            parameter.
     */
    public PaillierUtils(int bitLengthVal, int certainty) {
        KeyGeneration(bitLengthVal, certainty);
    }

    /**
     * Constructs an instance of the PaillierUtils cryptosystem with 512 bits of
     * modulus and at least 1-2^(-64) certainty of primes generation.
     */
    public PaillierUtils() {
        KeyGeneration(512, 64);
    }

    /**
     * Sets up the public key and private key.
     *
     * @param bitLengthVal
     *            number of bits of modulus.
     * @param certainty
     *            The probability that the new BigInteger represents a prime
     *            number will exceed (1 - 2^(-certainty)). The execution time of
     *            this constructor is proportional to the value of this
     *            parameter.
     */
    public void KeyGeneration(int bitLengthVal, int certainty) {
        bitLength = bitLengthVal;
        /*
         * Constructs two randomly generated positive BigIntegers that are
         * probably prime, with the specified bitLength and certainty.
         */
        // p = new BigInteger(bitLength / 2, certainty, new Random());
        // p = new BigInteger("68046914030173325108308053401410872030622523381986531890719664888517988060483");
        p = new BigInteger("11");
        // System.out.println(p);
        // q = new BigInteger(bitLength / 2, certainty, new Random());
        q = new BigInteger("13");
        // q = new BigInteger("86230928732135173671456009775544272778308895450924250977934895222099676822623");
        // System.out.println(q);
        /**
         * encrypted sum: 13786338157259894781045559560071029576391901065145345692460495170109534736517607008281523024863968470956291148897348055758572769665877299290234611577732539913767965684590187089114285098442249976410327791846307195033633947098204055627248715481399249054989147617264362596875058405426502048993957343870191911468
         */
        n = p.multiply(q);
        nsquare = n.multiply(n);

        g = new BigInteger("2");
        lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))
                .divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
        /* check whether g is good. */
        if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
            System.out.println("g is not good. Choose g again.");
            System.exit(1);
        }
    }

    public static synchronized PaillierUtils getInstance(){
        if (null == paillierUtils) {
            paillierUtils = new PaillierUtils();
        }
        return paillierUtils;
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function
     * explicitly requires random input r to help with encryption.
     *
     * @param m
     *            plaintext as a BigInteger
     * @param r
     *            random plaintext to help with encryption
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(final BigInteger m, final BigInteger r) {
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
    }

    /**
     * Encrypts plaintext m. ciphertext c = g^m * r^n mod n^2. This function
     * automatically generates random input r (to help with encryption).
     *
     * @param m
     *            plaintext as a BigInteger
     * @return ciphertext as a BigInteger
     */
    public BigInteger Encryption(final BigInteger m) {
        BigInteger r = new BigInteger(bitLength, new Random());
        return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);

    }

    /**
     * Decrypts ciphertext c. plaintext m = L(c^lambda mod n^2) * u mod n, where
     * u = (L(g^lambda mod n^2))^(-1) mod n.
     *
     * @param c
     *            ciphertext as a BigInteger
     * @return plaintext as a BigInteger
     */
    public BigInteger Decryption(final BigInteger c) {
        BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
        return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
    }

    /**
     * sum of (cipher) em1 and em2
     *
     * @param em1
     * @param em2
     * @return
     */
    public BigInteger cipher_add(BigInteger em1, BigInteger em2) {
        return em1.multiply(em2).mod(nsquare);
    }

    public BigInteger addPlainAndCipher(final BigInteger plain, final BigInteger cipher) {
        BigInteger plainCipher = paillierUtils.Encryption(plain);
        return cipher.multiply(plainCipher).mod(nsquare);
    }


    /**
     * main function
     *
     * @param str
     *            intput string
     */
    public static void main(String[] str) {
        /* instantiating an object of PaillierUtils cryptosystem */
        PaillierUtils paillierUtils = new PaillierUtils();
        /* instantiating two plaintext msgs */
        BigInteger m1 = new BigInteger("20");
        BigInteger m2 = new BigInteger("60");
        /* encryption */
        BigInteger em1 = paillierUtils.Encryption(m1);
        BigInteger em2 = paillierUtils.Encryption(m2);
        /* printout encrypted text */
        System.out.println(em1);
        System.out.println(em2);
        /* printout decrypted text */
        System.out.println(paillierUtils.Decryption(em1).toString());
        System.out.println(paillierUtils.Decryption(em2).toString());

        /*
         * test homomorphic properties -> D(E(m1)*E(m2) mod n^2) = (m1 + m2) mod
         * n
         */
        // m1+m2,求明文数值的和
        BigInteger sum_m1m2 = m1.add(m2).mod(paillierUtils.n);
        System.out.println("original sum: " + sum_m1m2.toString());
        // em1+em2，求密文数值的乘
        BigInteger product_em1em2 = em1.multiply(em2).mod(paillierUtils.nsquare);
        System.out.println("encrypted sum: " + product_em1em2.toString());
        System.out.println("decrypted sum: " + paillierUtils.Decryption(product_em1em2).toString());

        /* test homomorphic properties -> D(E(m1)^m2 mod n^2) = (m1*m2) mod n */
        // m1*m2,求明文数值的乘
        BigInteger prod_m1m2 = m1.multiply(m2).mod(paillierUtils.n);
        System.out.println("original product: " + prod_m1m2.toString());
        // em1的m2次方，再mod paillierUtils.nsquare
        BigInteger expo_em1m2 = em1.modPow(m2, paillierUtils.nsquare);
        System.out.println("encrypted product: " + expo_em1m2.toString());
        System.out.println("decrypted product: " + paillierUtils.Decryption(expo_em1m2).toString());

        //sum test
        System.out.println("--------------------------------");
        PaillierUtils p = new PaillierUtils();
        BigInteger t1 = new BigInteger("21");
        System.out.println(t1.toString());
        BigInteger t2 = new BigInteger("50");
        System.out.println(t2.toString());
        BigInteger t3 = new BigInteger("50");
        System.out.println(t3.toString());
        // BigInteger et1 = p.Encryption(t1);
        BigInteger et1 = new BigInteger("3925839048168323076653779224735261812694605855120855471750790800470382283157534912304777099275100639069771120094032618149557957133578222328807610247366914760028577009127181240572285694160389780411070405666134119379550913289448821847336457641386810662568577357664285694965602479860755308331534228102306851784");
        System.out.println(et1.toString());
        BigInteger et2 = p.Encryption(t2);
        System.out.println(et2.toString());
        BigInteger et3 = p.Encryption(t3);
        System.out.println(et3.toString());
        BigInteger sum = new BigInteger("1");
        sum = p.cipher_add(sum, et1);
        sum = p.cipher_add(sum, et2);
        sum = p.cipher_add(sum, et3);
        System.out.println("sum: " + sum.toString());
        System.out.println("decrypted sum: " + p.Decryption(sum).toString());
        System.out.println("--------------------------------");
    }
}