package com.maamria.algorithms;

import com.maamria.model.Primality;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigInteger;

import static com.maamria.algorithms.PrimalityUtils.*;

/**
 * @author maamria
 *         <p/> 26/06/2016
 */
@Component
public class MillerRabinAlgorithm implements PrimalityAlgorithm {

    @Override
    public Primality isProbablePrime(BigInteger number, int iterations) {
        Assert.notNull(number);
        if(number.compareTo(TWO) < 0){
            return Primality.NOT_APPLICABLE;
        }
        if(number.equals(TWO)){
            return Primality.PRIME;
        }
        if(number.mod(TWO).equals(ZERO)){
            return Primality.COMPOSITE;
        }
        int s = 0;
        BigInteger d = number.subtract(ONE);
        while (d.mod(TWO).equals(ZERO)) {
            s++;
            d = d.divide(TWO);
        }
        for (int i = 0; i < iterations; i++) {
            BigInteger a = uniformRandom(TWO, number.subtract(ONE));
            BigInteger x = a.modPow(d, number);
            if (x.equals(ONE) || x.equals(number.subtract(ONE)))
                continue;
            int r = 1;
            for (; r < s; r++) {
                x = x.modPow(TWO, number);
                if (x.equals(ONE))
                    return Primality.COMPOSITE;
                if (x.equals(number.subtract(ONE)))
                    break;
            }
            if (r == s) // None of the steps made x equal n-1.
                return Primality.COMPOSITE;
        }
        return Primality.PRIME;
    }

    @Override
    public String algorithmName() {
        return PrimalityUtils.MILLER_RABIN_ALGORITHM;
    }

}
