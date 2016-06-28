package com.maamria.model;

import java.math.BigInteger;

/**
 * A model for neighbouring prime result that includes: 1) whether the number itself is prime, 2) the smallest prime greater than the number, and
 * 3) the largest prime smaller that the number.
 *
 * @author maamria
 *         <p/> 26/06/2016
 */
public final class NeighbouringPrimesResult {

    private final BigInteger number;

    private final Primality numberPrimality;

    private final BigInteger nextNumber;

    private final BigInteger previousNumber;

    public NeighbouringPrimesResult(BigInteger number, Primality numberPrimality,
                                    BigInteger nextNumber, BigInteger previousNumber) {
        this.number = number;
        this.numberPrimality = numberPrimality;
        this.nextNumber = nextNumber;
        this.previousNumber = previousNumber;
    }

    /**
     * Returns the number.
     * @return number
     */
    public BigInteger getNumber() {
        return number;
    }

    /**
     * Returns the {@link Primality} of the number.
     * @return primality of number
     */
    public Primality getNumberPrimality() {
        return numberPrimality;
    }

    /**
     * Returns the smallest prime greater than the number
     * @return next prime
     */
    public BigInteger getNextNumber() {
        return nextNumber;
    }

    /**
     * Returns the largest prime smaller that the number.
     * @return previous prime
     */
    public BigInteger getPreviousNumber() {
        return previousNumber;
    }
}
