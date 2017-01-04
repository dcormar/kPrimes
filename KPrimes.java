package challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class KPrimes {
	
	static List <Long> kPrimes;
	static List <Long> primes;
	static long maxEvaluated = 2;
	static long minEvaluated = Long.MAX_VALUE;
	static int numberGetPrimes =0;
	static int nonPrimes =0;	
	static long getPrimesTotal = 0;
  
  public static void loadKPrimes (long multiplier, int lastElement, int round, int k, long start, long end) {
		if (k==round) {
			if (multiplier>=start) kPrimes.add(multiplier);
		}
		else { 
			for (int i=lastElement;i<primes.size();i++) {
				long nextVal = primes.get(i)*multiplier;
				if (nextVal <= end)	loadKPrimes (nextVal, i, round+1, k, start,end);
				else break;
			}
		}
	}
  
  public static long[] countKprimes(int k, long start, long end) {
		kPrimes = new ArrayList<Long>();
		evaluatePrimes((long)Math.floor((double)end/(double)Math.pow(2, k-1)));
		if (k==1) kPrimes.addAll(primes.stream()
										.filter(x -> x>=start && x<=end)
										.collect(Collectors.toList()));
		else loadKPrimes (1, 0, 0, k, start<end?start:end, end>start?end:start);
    long[] array = kPrimes.stream().mapToLong(l->l.longValue())
				.sorted()
				.toArray();
		return array;
	}
  public static int puzzle(int s) {
    	if (s < 138) return 0;
    	long [] prime = countKprimes(1, 2, s);
    	long [] threePrime = countKprimes(3, 2, s);
    	long [] sevenPrime = countKprimes(7, 2, s);
    	
    	if (prime == null || threePrime == null || sevenPrime == null) return 0;
    	else {
	    	return (int) LongStream.of(sevenPrime)
	    			.map(x -> LongStream.of(threePrime)
	    					.filter(f -> (x+f < (long) s))
	    					.map(y -> LongStream.of(prime)
	    							.filter(z -> (x+y+z == (long) s))
	    							.count()
	    							)
	    					.sum())
	    			.sum();   			
    	}
    }
    
    public static void evaluatePrimes (long value) {
    	getPrimes (2, value);
    }
    public static void getPrimes (long start, long end) {
    	//long epochStart = System.currentTimeMillis();
    	if (maxEvaluated == 2) {
    		primes = new ArrayList <Long> (10000);
    		primes.add((long)2);
    	}
    	if (maxEvaluated<end) {
    		nonPrimes ++;
    		fillPrimes(maxEvaluated+1,end);
    		maxEvaluated = end;
        	//epochStart = System.currentTimeMillis();
    	}
    	numberGetPrimes ++;
    	//long epochEnd = System.currentTimeMillis();
    	//getPrimesTotal += epochEnd - epochStart;
    	//return primes.stream().filter(x -> x>=start && x<=end).mapToLong(l->l.longValue()).toArray();
    }
    
    public static void fillPrimes(long start, long end) {
    	//long epochStart = System.currentTimeMillis();
    	boolean test = true;
    	for (long j=start;j<=end;j++) {
    		int i=0;
    		do {
    			if(j%primes.get(i)==0) test = false;
    			i++;
    		}while(i<primes.size() && test == true);
    		if (test==true) primes.add(j);
    		test = true;
    	}
    	//long epochEnd = System.currentTimeMillis();
    	//System.out.println("time - primes: " + (epochEnd - epochStart));
    }

	public static void main(String[] args) {
		//long[] array = KPrimes.countKprimes(5, 1000, 1100);
	   //LongStream.of(KPrimes.countKprimes(5, 1000, 1100)).forEach(System.out::println);
		System.out.println("Solution: " + Arrays.toString(KPrimes.countKprimes(5, 1000, 1100)));
	   System.out.println(puzzle(143));
	   //System.out.println(Arrays.toString(primes.stream().toArray()));
	   //System.out.println(primes.size());
	   //System.out.println("getPrimesTotal: " + getPrimesTotal);
	}

}
