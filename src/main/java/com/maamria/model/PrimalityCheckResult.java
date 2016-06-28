package com.maamria.model;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Result of checking whether a given number is prime.
 *
 * @author maamria
 *         <p/> 26/06/2016
 */
public final class PrimalityCheckResult {

    private final BigInteger number;

    private final Primality primality;

    public PrimalityCheckResult(BigInteger number, Primality primality) {
        this.number = number;
        this.primality = primality;
    }

    /**
     * Returns the number.
     * @return number
     */
    public BigInteger getNumber() {
        return number;
    }

    /**
     * Returns the number's primality.
     * @return primality
     */
    public Primality getPrimality() {
        return primality;
    }
}
