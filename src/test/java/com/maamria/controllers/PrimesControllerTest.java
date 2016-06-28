package com.maamria.controllers;

import com.maamria.PrimesApplication;
import com.maamria.algorithms.PrimalityUtils;
import com.maamria.model.Primality;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author maamria
 *         <p/> 27/06/2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PrimesApplication.class)
@WebAppConfiguration
public class PrimesControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void test_is_prime_with_negative_number() throws Exception{
        MockHttpServletRequestBuilder request = get("/primes/-11").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(-11))
                .andExpect(jsonPath("$.primality").value(Primality.NOT_APPLICABLE.name()));
    }

    @Test
    public void test_is_prime_no_number() throws Exception{
        MockHttpServletRequestBuilder request = get("/primes").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    public void test_is_prime_two() throws Exception {
        MockHttpServletRequestBuilder request = get("/primes/2").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(2))
            .andExpect(jsonPath("$.primality").value(Primality.PRIME.name()));
    }

    @Test
    public void test_is_prime_even_number_gt_two() throws Exception {
        MockHttpServletRequestBuilder request = get("/primes/100").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(100))
                .andExpect(jsonPath("$.primality").value(Primality.COMPOSITE.name()));
    }

    @Test
    public void test_is_prime() throws Exception {
        //
        MockHttpServletRequestBuilder request = get("/primes/59").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(59))
                .andExpect(jsonPath("$.primality").value(Primality.PRIME.name()));

        request = get("/primes/57").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(57))
                .andExpect(jsonPath("$.primality").value(Primality.COMPOSITE.name()));
        //
        request = get("/primes/712818121821828128738871217271271277177127171").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(new BigInteger("712818121821828128738871217271271277177127171")))
                .andExpect(jsonPath("$.primality").value(Primality.COMPOSITE.name()));
        //
        request = get("/primes/12312371823161").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(12312371823161L))
                .andExpect(jsonPath("$.primality").value(Primality.PRIME.name()));
        //
        request = get("/primes/712812391123207").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(712812391123207L))
                .andExpect(jsonPath("$.primality").value(Primality.PRIME.name()));
        //
        request = get("/primes/712812391123211").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(712812391123211L))
                .andExpect(jsonPath("$.primality").value(Primality.COMPOSITE.name()));

        request = get("/primes/9123812391291921283").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(9123812391291921283L))
                .andExpect(jsonPath("$.primality").value(Primality.PRIME.name()));

        request = get("/primes/712818121821828128738871217271271277177127257").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(new BigInteger("712818121821828128738871217271271277177127257")))
                .andExpect(jsonPath("$.primality").value(Primality.PRIME.name()));

    }

    @Test
    public void test_get_neighbouring_primes_no_number() throws Exception{
        MockHttpServletRequestBuilder request = get("/primes/neighbours").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    public void test_get_neighbouring_primes_for_negative_number() throws Exception{
        MockHttpServletRequestBuilder request = get("/primes/neighbours/-11").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(-11))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.NOT_APPLICABLE.name()))
                .andExpect(jsonPath("$.nextNumber").value(2))
                .andExpect(jsonPath("$.previousNumber").doesNotExist());

        request = get("/primes/neighbours/0").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.NOT_APPLICABLE.name()))
                .andExpect(jsonPath("$.nextNumber").value(2))
                .andExpect(jsonPath("$.previousNumber").doesNotExist());

        request = get("/primes/neighbours/1").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.NOT_APPLICABLE.name()))
                .andExpect(jsonPath("$.nextNumber").value(2))
                .andExpect(jsonPath("$.previousNumber").doesNotExist());
    }

    @Test
    public void test_get_neighbouring_primes_two() throws Exception{
        MockHttpServletRequestBuilder request = get("/primes/neighbours/2").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(2))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.PRIME.name()))
                .andExpect(jsonPath("$.nextNumber").value(3))
                .andExpect(jsonPath("$.previousNumber").doesNotExist());
    }

    @Test
    public void test_get_neighbouring_primes_three() throws Exception{
        MockHttpServletRequestBuilder request = get("/primes/neighbours/3").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(3))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.PRIME.name()))
                .andExpect(jsonPath("$.nextNumber").value(5))
                .andExpect(jsonPath("$.previousNumber").value(2));
    }

    @Test
    public void test_get_neighbouring_primes() throws Exception{
        MockHttpServletRequestBuilder request = get("/primes/neighbours/5").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(5))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.PRIME.name()))
                .andExpect(jsonPath("$.nextNumber").value(7))
                .andExpect(jsonPath("$.previousNumber").value(3));

        request = get("/primes/neighbours/57").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(57))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.COMPOSITE.name()))
                .andExpect(jsonPath("$.nextNumber").value(59))
                .andExpect(jsonPath("$.previousNumber").value(53));

        request = get("/primes/neighbours/8812919").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(8812919))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.COMPOSITE.name()))
                .andExpect(jsonPath("$.nextNumber").value(8812939))
                .andExpect(jsonPath("$.previousNumber").value(8812913));

        request = get("/primes/neighbours/8812918").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.number").value(8812918))
                .andExpect(jsonPath("$.numberPrimality").value(Primality.COMPOSITE.name()))
                .andExpect(jsonPath("$.nextNumber").value(8812939))
                .andExpect(jsonPath("$.previousNumber").value(8812913));
    }

}