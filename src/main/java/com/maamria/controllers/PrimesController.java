package com.maamria.controllers;

import com.maamria.algorithms.PrimalityUtils;
import com.maamria.model.NeighbouringPrimesResult;
import com.maamria.model.Primality;
import com.maamria.model.PrimalityCheckResult;
import com.maamria.services.PrimesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * The controller serving requests to check whether a given number is prime. It also provides facility to query
 * neighbouring primes to a given number.
 *
 * @author maamria
 *         <p/> 26/06/2016
 */
@RestController
@RequestMapping("/primes")
public class PrimesController {

    private static final Logger log = LoggerFactory.getLogger(PrimesController.class);

    private final PrimesService primesService;

    @Autowired
    public PrimesController(PrimesService primesService) {
        this.primesService = primesService;
    }

    /**
     * Checks whether the argument <code>number</code> is prime.
     * @param number number to check for primality
     * @return response with {@link PrimalityCheckResult}
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{number}")
    public ResponseEntity<PrimalityCheckResult> isPrime(@PathVariable("number") BigInteger number){
        log.info("Request checking primality of number {}", number);
        if(number.compareTo(BigInteger.ONE) <= 0){
            return ResponseEntity.ok(new PrimalityCheckResult(number, Primality.NOT_APPLICABLE));
        }
        Primality primality = primesService.checkPrimality(number);
        PrimalityCheckResult primalityCheckResult = new PrimalityCheckResult(number, primality);
        return ResponseEntity.ok(primalityCheckResult);
    }

    /**
     * Responds the neighbouring primes to the given number.
     * @param number number to get neighbours for
     * @return response with {@link NeighbouringPrimesResult}
     */
    @RequestMapping(method = RequestMethod.GET, value = "/neighbours/{number}")
    public ResponseEntity<NeighbouringPrimesResult> getNeighbours(@PathVariable("number") BigInteger number){
        log.info("Request getting prime neighbours of number {}", number);
        if(number.compareTo(BigInteger.ONE) <= 0){
            return ResponseEntity.ok(new NeighbouringPrimesResult(number, Primality.NOT_APPLICABLE, PrimalityUtils.TWO, null));
        }
        NeighbouringPrimesResult neighbouringPrimes = primesService.getNeighbouringPrimes(number);
        return ResponseEntity.ok(neighbouringPrimes);
    }

}
