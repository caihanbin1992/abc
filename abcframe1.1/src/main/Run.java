package main;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import fun.Function;
import abc.ABC;


public class Run implements Runnable{
	public static String abc = "abc.";
	public static String lit = "literature.";
	public static String imp = "improve.";
	public static String fac = "improve.factor.";
	public static String confPath = "src/conf/";
	public static String fp = confPath + "function.properties", 
			pp = confPath + "param.properties";
	public static String outputfile = 
			"E:\\Ph.D Files\\Paper\\Experiment\\data\\abcexperiment\\";
	static List<String> funNames = null;
	protected String algorithm = "ABC";
	static {
		try {
			funNames = PropertyReader.getAllValues(fp);
			Collections.sort(funNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Run(){}
	
	public Run(String algorithm){
		this.algorithm = algorithm;
	}
	
	@Override
	public void run(){
		try {
			abc(algorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abc(String algorithm) throws Exception{
		String a = algorithm;
		String b = a + "******************************" + a;
		System.out.println(b);
		NoteWriter.write(b + "\r\n", outputfile+algorithm+".txt", true);
		double lb, ub; int maxCycle, D; String function;
		for (String f : funNames) {
			function = f.substring(0, f.indexOf("."));
			D = Integer.valueOf(f.substring(f.lastIndexOf(".")+1, f.length()-1));
			lb = Double.valueOf(PropertyReader.GetValueByKey(pp, function+"."+"lb"));
			ub = Double.valueOf(PropertyReader.GetValueByKey(pp, function+"."+"ub"));
			maxCycle = Integer.valueOf(PropertyReader.GetValueByKey(pp, f+"."+"maxCycle"));
			runABC(getABC(algorithm, function, D, maxCycle, lb, ub));
//			System.out.println(f + "->" + lb + "," + ub + "," + maxCycle);
		}
		NoteWriter.write("\r\n\r\n", outputfile+algorithm+".txt", true);
	}
	
	public static ABC getABC(String algorithm, String function, int D, int maxCycle,
			double lb, double ub) {
		ABC bee = null;
		Class<?> clazz = getClazz(algorithm);
		
		try {
			Class<?>[] paramTypes = {String.class, int.class, int.class, double.class, double.class};
			Object[] params = {function, D, maxCycle, lb, ub};
			Constructor<?> con = clazz.getConstructor(paramTypes);
			bee = (ABC)con.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(bee.getClass().getName());
		return bee;
	}
	
	public static Class<?> getClazz(String algorithm) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(abc + algorithm);
		} catch (Exception e) {
			try {
				clazz = Class.forName(lit + algorithm);
			} catch (ClassNotFoundException e1) {
				try {
					clazz = Class.forName(imp + algorithm);
				} catch (ClassNotFoundException e2) {
					try {
						clazz = Class.forName(fac + algorithm);
					} catch (ClassNotFoundException e3) {
						e3.printStackTrace();
					}
				}
			}
		}
		return clazz;
	}
	
	public void runABC (ABC bee) throws Exception{
		int iter=0;
		int run=0;
		double mean=0;
		for(run=0;run<bee.runtime;run++)//
		{
			bee.initial();
			bee.MemorizeBestSource();
			for (iter=0;iter<bee.maxCycle;iter++)
			{
				bee.SendEmployedBees(iter);
				bee.CalculateProbabilities();
				bee.SendOnlookerBees(iter);
				bee.SendScoutBees(iter);
				bee.MemorizeBestSource();
				bee.LocalSearch(iter);
			 }
			
			 bee.GlobalMins[run]=bee.GlobalMin;
			 mean=mean+bee.GlobalMin;
		}
		mean=mean/bee.runtime;
		/*String a = bee.getClass().getName();
		a = a.substring(a.lastIndexOf(".")+1,a.length());
		System.out.println(a + "->" + bee.fun + " D="+ bee.D + " Means  of "+bee.runtime+"runs: "+mean +
				" SD is " + sd(mean, bee.GlobalMins, bee.runtime));
		NoteWriter.write(a + "->" + bee.fun + " D="+ bee.D + " Means  of "+bee.runtime+"runs: "+mean +
				" SD is " + sd(mean, bee.GlobalMins, bee.runtime) + "\r\n",
				outputfile + a + ".txt", true);*/
		resultOutpur(bee, mean);
	}
	
	/**
	 * Result output.
	 * @param bee
	 * @param mean
	 * @throws Exception
	 */
	public void resultOutpur(ABC bee, double mean) throws Exception{
		String a = bee.getClass().getName();
		a = a.substring(a.lastIndexOf(".")+1,a.length());
		System.out.println(a + "->" + bee.fun + " D="+ bee.D + " Means  of "+bee.runtime+"runs: "+mean +
				" SD is " + sd(mean, bee.GlobalMins, bee.runtime));
		NoteWriter.write(a + "->" + bee.fun + " D="+ bee.D + " Means  of "+bee.runtime+"runs: "+mean +
				" SD is " + sd(mean, bee.GlobalMins, bee.runtime) + "\r\n",
				outputfile + a + ".txt", true);
	}
	

	
	public double sd(double mean, double[] xi, int N) {
		double res = 0;
		for (double d : xi) {
			res += (d - mean) * (d - mean);
		}
		res /= N;
		res = Math.sqrt(res);
		return res;
	}

}
