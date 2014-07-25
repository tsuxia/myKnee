package ca.utoronto.ece1778.services;

import java.util.Arrays;

public class BenchmarkService
{

	double[] normalMean = new double[75];
	double[] normalStdv = new double[75];
	
	double[] aclBenchmark = new double[27];

	public BenchmarkService()
	{
		Arrays.fill(normalMean, 0, 6, 141.7);
		Arrays.fill(normalStdv, 0, 6, 6.2);
		
		Arrays.fill(normalMean, 6, 13, 141.7);
		Arrays.fill(normalStdv, 6, 13, 3.5);
		
		Arrays.fill(normalMean, 13, 20, 142.9);
		Arrays.fill(normalStdv, 13, 20, 3.7);
		
		Arrays.fill(normalMean, 20, 30, 140.2);
		Arrays.fill(normalStdv, 20, 30, 5.2);
		
		Arrays.fill(normalMean, 30, 40, 134);
		Arrays.fill(normalStdv, 30, 40, 9);
		
		Arrays.fill(normalMean, 40, 60, 132);
		Arrays.fill(normalStdv, 40, 60, 11);
		
		Arrays.fill(normalMean, 60, 75, 131);
		Arrays.fill(normalStdv, 60, 75, 11);
		
		aclBenchmark[1] = 90;
		aclBenchmark[2] = 120;
		aclBenchmark[3] = 130;
		aclBenchmark[4] = 132;
		aclBenchmark[5] = 135;
		Arrays.fill(aclBenchmark, 6, 27, 140);
	}
	
	public Pair getNormalBenchmark(int age)
	{
		if(age < 0 || age > 74)
			age = 74;
		Pair pair = new Pair(normalMean[age], normalStdv[age]);
		return pair;
	}
	
	public double getACLBenchmark(long week)
	{
		if(week > 26 || week < 0)
			week = 26;
		return aclBenchmark[(int) week];
	}

	public class Pair
	{
		
		public double mean, std;
		public Pair(double mean, double std)
		{
			this.mean = mean;
			this.std = std;
		}
	}
	
}
