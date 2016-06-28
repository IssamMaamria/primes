package com.maamria.algorithms;

import com.maamria.model.Primality;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * @author maamria
 *         <p/> 28/06/2016
 */
class AlgorithmTests {

    PrimalityAlgorithm algorithm;

    void test_check_prime_with_number_less_than_two(){
        assertEquals(Primality.NOT_APPLICABLE, algorithm.isProbablePrime(BigInteger.ONE, 10));
        assertEquals(Primality.NOT_APPLICABLE, algorithm.isProbablePrime(BigInteger.ZERO, 10));
        assertEquals(Primality.NOT_APPLICABLE, algorithm.isProbablePrime(BigInteger.valueOf(-11L), 10));
    }

    void test_check_prime_of_two(){
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(PrimalityUtils.TWO, 10));
    }

    void test_check_prime_even_positive_number_gt_two(){
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(4L), 10));
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(100L), 10));
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(1912128L), 10));
    }

    void test_check_prime(){
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(4L), 10));
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(100L), 10));
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(1912128L), 10));
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(57L), 10));
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(12126161211L), 10));
        assertEquals(Primality.COMPOSITE, algorithm.isProbablePrime(BigInteger.valueOf(19121211L), 10));

        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(3L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(7L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(19L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(59L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(121231231243L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(121259L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(81231781229L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(BigInteger.valueOf(121259L), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(new BigInteger("1216771278188912912121212121283"), 10));
        assertEquals(Primality.PRIME, algorithm.isProbablePrime(new BigInteger("99991219999199199161"), 10));

    }
}
