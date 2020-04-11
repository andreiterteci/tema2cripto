package ro.fmiunibuv.tema2cripto.rest;

import java.math.BigInteger;
import java.util.Random;

public class BlumBlumShub implements RandomGenerator{

    private static final BigInteger three = BigInteger.valueOf(3L);

    private static final BigInteger four = BigInteger.valueOf(4L);

    private BigInteger M;

    private BigInteger state;

    private static BigInteger getPrime(int bits, Random rand) {
        BigInteger p;
        while (true) {
            p = new BigInteger(bits, 100, rand);
            if (p.mod(four).equals(three))
                break;
        }
        return p;
    }

    public static BigInteger generateM(int bits, Random rand) {
        BigInteger p = getPrime(bits/2, rand);
        BigInteger q = getPrime(bits/2, rand);

        // trebuie verificat ca p este diferit de q
        while (p.equals(q)) {
            q = getPrime(bits, rand);
        }
        return p.multiply(q);
    }

    public BlumBlumShub(BigInteger M, byte[] seed) {
        this.M = M;
        setSeed(seed);
    }

    public void setSeed(byte[] seedBytes) {
        BigInteger seed = new BigInteger(1, seedBytes);
        state = seed.mod(M);
    }

    public BigInteger next(int seed) {
       state = (state.multiply(state)).mod(M);
       return state;
    }
}
