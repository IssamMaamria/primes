package com.maamria.algorithms;

import com.maamria.model.Primality;

import java.math.BigInteger;

/**
 * Protocol for primality-checking algorithm
 *
 * @author maamria
 *         <p/> 26/06/2016
 */
public interface PrimalityAlgorithm {

    /**
     * Returns the primality of <code>number</code>.
     * @param number number, not null
     * @param iterations number of iterations
     * @return whether <code>number</code> is (probable) prime.
     */
    Primality isProbablePrime(BigInteger number, int iterations);

    /**
     * Returns algorithm name.
     * @return algorithm name
     */
    String algorithmName();

}
