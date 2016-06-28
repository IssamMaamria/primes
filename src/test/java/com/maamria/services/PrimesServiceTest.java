package com.maamria.services;

import com.maamria.algorithms.FermatAlgorithm;
import com.maamria.algorithms.MillerRabinAlgorithm;
import com.maamria.algorithms.PrimalityAlgorithm;
import com.maamria.algorithms.PrimalityUtils;
import com.maamria.model.NeighbouringPrimesResult;
import com.maamria.model.Primality;
import com.maamria.services.impl.DefaultPrimesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.task.SyncTaskExecutor;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import static com.maamria.algorithms.PrimalityUtils.*;
import static org.junit.Assert.*;

/**
 * @author maamria
 *         <p/> 27/06/2016
 */
public class PrimesServiceTest {

    private PrimesService primesService;

    @Before
    public void setup(){
        Set<PrimalityAlgorithm> algorithmSet = new HashSet<PrimalityAlgorithm>(){
            // should get away with this in testing
            {
                add(new FermatAlgorithm());
                add(new MillerRabinAlgorithm());
            }
        };
        Executor executor = new SyncTaskExecutor();
        primesService = new DefaultPrimesService(algorithmSet, executor, 25);
    }

    @Test(expected = NullPointerException.class)
    public void test_check_primality_with_null_number(){
        primesService.checkPrimality(null);
    }

    @Test
    public void test_check_primality_of_two(){
        Primality primality = primesService.checkPrimality(TWO);
        assertEquals(Primality.PRIME, primality);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_check_primality_of_number_lt_two(){
        try{
            primesService.checkPrimality(BigInteger.ONE);
            assertTrue(false);
        } catch (IllegalArgumentException e){
            // expected
        }
        primesService.checkPrimality(BigInteger.valueOf(-11L));
    }

    @Test
    public void test_check_primality_of_positive_even_number_gt_two(){
        Primality p1 = primesService.checkPrimality(BigInteger.valueOf(4L));
        assertEquals(Primality.COMPOSITE, p1);
        Primality p2 = primesService.checkPrimality(BigInteger.valueOf(41212812L));
        assertEquals(Primality.COMPOSITE, p2);
    }

    @Test
    public void test_check_primality(){
        Primality p1 = primesService.checkPrimality(BigInteger.valueOf(11L));
        assertEquals(Primality.PRIME, p1);
        p1 = primesService.checkPrimality(BigInteger.valueOf(19L));
        assertEquals(Primality.PRIME, p1);
        p1 = primesService.checkPrimality(BigInteger.valueOf(99L));
        assertEquals(Primality.COMPOSITE, p1);
        p1 = primesService.checkPrimality(new BigInteger("14352111571112311211"));
        assertEquals(Primality.PRIME, p1);
        p1 = primesService.checkPrimality(new BigInteger("14352111571112311307"));
        assertEquals(Primality.PRIME, p1);
        p1 = primesService.checkPrimality(new BigInteger("14352111571112311308"));
        assertEquals(Primality.COMPOSITE, p1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_get_neighbouring_primes_of_null_number(){
        primesService.getNeighbouringPrimes(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_get_neighbouring_primes_of_number_lt_two(){
        try{
            primesService.getNeighbouringPrimes(BigInteger.ONE);
            assertTrue(false);
        } catch (IllegalArgumentException e){
            // expected
        }
        primesService.getNeighbouringPrimes(BigInteger.valueOf(-11L));
    }

    @Test
    public void test_get_neighbouring_primes_of_two(){
        NeighbouringPrimesResult neighbouringPrimes = primesService.getNeighbouringPrimes(PrimalityUtils.TWO);
        assertEquals(Primality.PRIME, neighbouringPrimes.getNumberPrimality());
        assertEquals(PrimalityUtils.THREE, neighbouringPrimes.getNextNumber());
        assertNull(neighbouringPrimes.getPreviousNumber());
    }

    @Test
    public void test_get_neighbouring_primes_of_three(){
        NeighbouringPrimesResult neighbouringPrimes = primesService.getNeighbouringPrimes(PrimalityUtils.THREE);
        assertEquals(Primality.PRIME, neighbouringPrimes.getNumberPrimality());
        assertEquals(BigInteger.valueOf(5), neighbouringPrimes.getNextNumber());
        assertEquals(PrimalityUtils.TWO, neighbouringPrimes.getPreviousNumber());
    }

    @Test
    public void test_get_neighbouring(){
        NeighbouringPrimesResult neighbouringPrimes = primesService.getNeighbouringPrimes(BigInteger.valueOf(8812919));
        assertEquals(Primality.COMPOSITE, neighbouringPrimes.getNumberPrimality());
        assertEquals(BigInteger.valueOf(8812939), neighbouringPrimes.getNextNumber());
        assertEquals(BigInteger.valueOf(8812913), neighbouringPrimes.getPreviousNumber());

        neighbouringPrimes = primesService.getNeighbouringPrimes(BigInteger.valueOf(671277171));
        assertEquals(Primality.COMPOSITE, neighbouringPrimes.getNumberPrimality());
        assertEquals(BigInteger.valueOf(671277179), neighbouringPrimes.getNextNumber());
        assertEquals(BigInteger.valueOf(671277157), neighbouringPrimes.getPreviousNumber());

    }
}