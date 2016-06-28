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
public class FermatAlgorithm implements PrimalityAlgorithm {

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
        BigInteger nMinus1 = number.subtract(ONE);
        for (int i = 0; i < iterations; i++){
            BigInteger random = PrimalityUtils.uniformRandom(TWO, number.subtract(ONE));
            BigInteger modPow = random.modPow(nMinus1, number);
            if(!modPow.equals(ONE)){
                return Primality.COMPOSITE;
            }
        }
        return Primality.PRIME;
    }

    @Override
    public String algorithmName() {
        return PrimalityUtils.FERMAT_ALGORITHM;
    }
}
