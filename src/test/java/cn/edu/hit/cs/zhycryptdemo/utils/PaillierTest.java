package cn.edu.hit.cs.zhycryptdemo.utils;

import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

public class PaillierTest {
    Random random = new Random(System.currentTimeMillis());
    Paillier paillier = new Paillier();
    BigInteger r = BigInteger.valueOf(random.nextInt(5)).add(BigInteger.valueOf(2));
    BigInteger r_pie = r.subtract(BigInteger.ONE);
    int t = 0;

    @Test
    public void test() {


        BigInteger x = BigInteger.valueOf(100);
        BigInteger y = BigInteger.valueOf(101);

        BigInteger x_enc = paillier.Encryption(x, r);
        BigInteger y_enc = paillier.Encryption(y, r);


        BigInteger x_dec = paillier.Decryption(x_enc);

        System.out.println(String.format("x %s,\n x_enc %s,\n x_dec %s", x, x_enc, x_dec));

        BigInteger x_mod_n_enc = paillier.Encryption(x.mod(paillier.n), r);
        System.out.println(String.format("x_mod_n_enc %s", x_mod_n_enc));
        System.out.println(x_enc.equals(x_mod_n_enc));


        BigInteger x1_enc = calculateX1Enc(x_enc);
        BigInteger y1_enc = calculateY1Enc(y_enc);

        BigInteger u_enc;
        BigInteger lt_enc;
        if (t == 0) {
            lt_enc = calculateL0Enc(x1_enc, y1_enc);
            u_enc = lt_enc;
        } else if (t == 1) {
            lt_enc = calculateL1Enc(x1_enc, y1_enc);
            u_enc = paillier.Encryption(BigInteger.ONE).multiply(lt_enc.modPow(BigInteger.valueOf(-1), paillier.nsquare)).mod(paillier.nsquare);
        } else {
            u_enc = BigInteger.valueOf(-1);
            lt_enc = BigInteger.valueOf(-1);
        }

        BigInteger lt_dec = paillier.Decryption(lt_enc);
        System.out.println(lt_dec);
        BigInteger u_dec = paillier.Decryption(u_enc);
        System.out.println(u_dec);

        BigInteger temp = BigInteger.valueOf(2).mod(paillier.n.divide(BigInteger.valueOf(2)));
        System.out.println(lt_dec.compareTo(temp));

    }


    @Test
    public void testAdd() {
        BigInteger x = BigInteger.valueOf(100);
        BigInteger y = BigInteger.valueOf(101);
        BigInteger x_enc = paillier.Encryption(x, r);
        BigInteger y_enc = paillier.Encryption(y, r);
        BigInteger x_add_y_enc = x_enc.multiply(y_enc).mod(paillier.nsquare);
        BigInteger x_add_y = paillier.Decryption(x_add_y_enc);
        System.out.println(x_add_y);

    }


    @Test
    public void testEncMultiply(){
        BigInteger x = BigInteger.valueOf(100);
        BigInteger y = BigInteger.valueOf(101);
        BigInteger x_enc = paillier.Encryption(x, r);
        BigInteger y_enc = paillier.Encryption(y, r);

        BigInteger rx = BigInteger.valueOf(random.nextInt(5)).add(BigInteger.valueOf(2));
        BigInteger ry = BigInteger.valueOf(random.nextInt(5)).add(BigInteger.valueOf(2));

        // BigInteger X =

    }


    public BigInteger calculateX1Enc(BigInteger x_enc) {
        BigInteger _2x_enc = x_enc.modPow(BigInteger.valueOf(2), paillier.nsquare);
        BigInteger x1_enc = _2x_enc.multiply(paillier.Encryption(BigInteger.ONE, r)).mod(paillier.nsquare);
        return x1_enc;
    }

    public BigInteger calculateY1Enc(BigInteger y_enc) {
        BigInteger _2y_enc = y_enc.modPow(BigInteger.valueOf(2), paillier.nsquare);
        BigInteger y1_enc = _2y_enc;
        return y1_enc;
    }


    public BigInteger calculateL0Enc(BigInteger x1_enc, BigInteger y1_enc) {
        return calculateLEnc(x1_enc, y1_enc);
    }

    public BigInteger calculateL1Enc(BigInteger x1_enc, BigInteger y1_enc) {
        return calculateLEnc(y1_enc, x1_enc);
    }


    public BigInteger calculateLEnc(BigInteger x1_enc, BigInteger y1_enc) {
        BigInteger LEnc = x1_enc.multiply(y1_enc.modPow(BigInteger.valueOf(-1), paillier.nsquare)).mod(paillier.nsquare).modPow(r, paillier.nsquare).multiply(paillier.Encryption(r_pie, r)).mod(paillier.nsquare);
        return LEnc;
    }


    @Test
    public void testAAAA() {
        System.out.println(BigInteger.valueOf(5652).modPow(BigInteger.valueOf(60), BigInteger.valueOf(77).pow(2)));
    }

}
