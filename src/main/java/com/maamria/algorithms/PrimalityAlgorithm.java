package com.maamria.algorithms;

import com.maamria.model.Primality;

import java.math.BigInteger;

/**
 * @author maamria
 *         <p/> 26/06/2016
 */
public interface PrimalityAlgorithm {

    Primality isProbablePrime(BigInteger number, int iterations);

    String algorithmName();

}
