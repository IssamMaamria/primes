package com.maamria.algorithms;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author maamria
 *         <p/> 27/06/2016
 */
public class PrimalityUtils {

    static final String FERMAT_ALGORITHM = "Fermat";
    static final String MILLER_RABIN_ALGORITHM = "MillerRabin";

    public static final BigInteger ZERO = BigInteger.ZERO;
    public static final BigInteger ONE = BigInteger.ONE;
    public static final BigInteger TWO = BigInteger.valueOf(2L);
    public static final BigInteger THREE = BigInteger.valueOf(3L);

    public static boolean isEven(BigInteger number){
        return number.mod(TWO).equals(ZERO);
    }

    static BigInteger uniformRandom(BigInteger bottom, BigInteger top){
        Random rnd = new Random();
        BigInteger result;
        do {
            result = new BigInteger(top.bitLength(), rnd);
        } while (result.compareTo(bottom) < 0 || result.compareTo(top) > 0);
        return result;
    }

}
