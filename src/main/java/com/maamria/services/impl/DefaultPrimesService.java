package com.maamria.services.impl;

import com.maamria.algorithms.PrimalityAlgorithm;
import com.maamria.algorithms.PrimalityUtils;
import com.maamria.model.NeighbouringPrimesResult;
import com.maamria.model.Primality;
import com.maamria.services.PrimesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author maamria
 *         <p/> 26/06/2016
 */
@Service
public class DefaultPrimesService implements PrimesService{

    private static final Logger log = LoggerFactory.getLogger(DefaultPrimesService.class);

    private final Set<PrimalityAlgorithm> primalityAlgorithms;
    private final Executor executor;
    private final int precision;

    @Autowired
    public DefaultPrimesService(Set<PrimalityAlgorithm> primalityAlgorithms,
                                Executor executor, @Value("${primes.check.iterations}") int precision) {
        this.primalityAlgorithms = primalityAlgorithms;
        this.executor = executor;
        this.precision = precision;
    }

    @Override
    public Primality checkPrimality(BigInteger number) {
        Objects.requireNonNull(number);
        Assert.isTrue(number.compareTo(PrimalityUtils.ONE) > 0, "Number needs to be greater than 1");
        if(PrimalityUtils.TWO.equals(number)){
            return Primality.PRIME;
        }
        if(PrimalityUtils.isEven(number)){
            return Primality.COMPOSITE;
        }
        log.debug("Checking primality of {}", number);
        Set<CompletableFuture<Pair<String, Primality>>> results = new HashSet<>();
        primalityAlgorithms.forEach(algorithm -> {
            CompletableFuture<Pair<String, Primality>> future =
                    CompletableFuture.supplyAsync(() -> new Pair<>(algorithm.algorithmName(), algorithm.isProbablePrime(number, precision)), executor)
                            .whenComplete((primality, throwable) -> {
                                if (throwable != null) {
                                    log.error("Primality check using algorithm " + algorithm.algorithmName() + " has failed.",
                                            throwable);
                                }
                            });
            results.add(future);
        });

        CompletableFuture.allOf(results.toArray(new CompletableFuture[results.size()])).join();

        Map<String, Primality> primalityMap = results.stream().filter(pairCompletableFuture -> !pairCompletableFuture.isCompletedExceptionally())
                .map(pairCompletableFuture -> {
                    try {
                        return pairCompletableFuture.get();
                    } catch (ExecutionException | InterruptedException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toMap(pair -> pair.first, pair -> pair.second));
        return primalityMap.values().stream().allMatch(p->p == Primality.PRIME) ? Primality.PRIME : Primality.COMPOSITE;
    }

    @Override
    public NeighbouringPrimesResult getNeighbouringPrimes(BigInteger number) {
        Assert.notNull(number, "Number cannot be null");
        Assert.isTrue(number.compareTo(PrimalityUtils.ONE) > 0, "Number needs to be greater than 1");
        if(number.equals(PrimalityUtils.TWO)){
            return new NeighbouringPrimesResult(PrimalityUtils.TWO, Primality.PRIME, PrimalityUtils.THREE, null);
        }
        if(number.equals(PrimalityUtils.THREE)){
            return new NeighbouringPrimesResult(PrimalityUtils.THREE, Primality.PRIME, BigInteger.valueOf(5L), PrimalityUtils.TWO);
        }
        Primality primality = checkPrimality(number);
        BigInteger startingUp, startingDown;
        if(!PrimalityUtils.isEven(number)){
            startingUp = number.add(PrimalityUtils.TWO);
            startingDown = number.subtract(PrimalityUtils.TWO);
        }
        else {
            startingUp = number.add(PrimalityUtils.ONE);
            startingDown = number.subtract(PrimalityUtils.ONE);
        }
        BigInteger startUp = startingUp;
        BigInteger startDown = startingDown;
        CompletableFuture<BigInteger> futureUp = CompletableFuture.supplyAsync(() -> {
            BigInteger starting = startUp;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                Primality prim = checkPrimality(starting);
                if (prim == Primality.PRIME) {
                    return starting;
                }
                starting = starting.add(PrimalityUtils.TWO);
            }
            throw new RuntimeException("Unable to find next prime within the integer range");
        }, executor);

        CompletableFuture<BigInteger> futureDown = CompletableFuture.supplyAsync(() -> {
            BigInteger starting = startDown;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                Primality prim = checkPrimality(starting);
                if (prim == Primality.PRIME) {
                    return starting;
                }
                starting = starting.subtract(PrimalityUtils.TWO);
            }
            throw new RuntimeException("Unable to find previous prime within the integer range");
        }, executor);
        CompletableFuture.allOf(futureDown, futureUp).join();
        try {
            BigInteger previousPrime = futureDown.get();
            BigInteger nextPrime = futureUp.get();
            return new NeighbouringPrimesResult(number, primality, nextPrime, previousPrime);
        } catch (InterruptedException|ExecutionException e) {
            log.error("Failed to calculate neighbours of "+ number, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Customary pair.
     * @param <U1> type of first
     * @param <U2> type of second
     */
    private final class Pair<U1, U2> {
        final U1 first;
        final U2 second;

        Pair(U1 first, U2 second){
            this.first = first;
            this.second = second;
        }
    }
}
