package com.maamria.services;

import com.maamria.model.NeighbouringPrimesResult;
import com.maamria.model.Primality;

import java.math.BigInteger;
import java.util.Map;

/**
 * Protocol for service that can check the primality of numbers. It should also provide neighbouring primes of a given number.
 * @author maamria
 *         <p/> 26/06/2016
 */
public interface PrimesService {

    /**
     * Checks whether the <code>number</code> is prime.
     * @param number not null and greater than 1
     * @return number {@link Primality}
     */
    Primality checkPrimality(BigInteger number);

    /**
     * Returns the neighbouring primes of <code>number</code>.
     * @param number not null and greater than 1
     * @return {@link NeighbouringPrimesResult}
     */
    NeighbouringPrimesResult getNeighbouringPrimes(BigInteger number);

}
