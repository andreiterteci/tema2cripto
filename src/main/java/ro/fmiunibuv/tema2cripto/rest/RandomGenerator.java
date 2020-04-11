package ro.fmiunibuv.tema2cripto.rest;

import java.math.BigInteger;

public interface RandomGenerator {
    BigInteger next(int numBits);
}
