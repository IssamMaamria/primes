# Prime Numbers Rest Service 

This simple maven-based project provides a web service for 1) checking whether a number is prime, 2) calculating the neighbouring primes to a given number. 

The rest service is written in Java 8, and is based on SpringBoot.

### Running the Service

To run the rest service:

```
git clone https://github.com/IssamMaamria/primes.git primes
cd primes
mvn spring-boot:run
```

This will compile and run the rest service as an application with an embedded tomcat server on port 9999.

### Endpoints
 1- Check primality: 
 ```
 GET http://localhost:9999/primes/{number}
 ```
 e.g., the following request 
 ```
 GET http://localhost:9999/primes/8812939
 ```
 returns the following JSON response:
 ```
  {
    "number": 8812939,
    "primality": "PRIME"
  }
 ```
 
 
 2- Get Neighbouring Primes: 
 ```
 GET http://localhost:9999/primes/neighbours/{number}
 ```
 e.g., the following request
 ```
 GET http://localhost:9999/primes/neighbours/671277171 
 ```
 returns the following JSON response:
```
  {
    "number": 671277171,
    "numberPrimality": "COMPOSITE",
    "nextNumber": 671277179,
    "previousNumber": 671277157
 }
 ```




