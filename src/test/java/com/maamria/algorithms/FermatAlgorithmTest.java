package com.maamria.algorithms;

import org.junit.Before;
import org.junit.Test;

/**
 * @author maamria
 *         <p/> 27/06/2016
 */
public class FermatAlgorithmTest extends AlgorithmTests{

    @Before
    public void setup(){
        algorithm = new FermatAlgorithm();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_check_prime_with_null_number(){
        algorithm.isProbablePrime(null, 10);
    }

    @Test
    public void test_check_prime_all(){
        test_check_prime_with_number_less_than_two();
        test_check_prime_of_two();
        test_check_prime_even_positive_number_gt_two();
        test_check_prime();
    }

}