package factorlib;

import java.lang.reflect.Method;
import java.util.Arrays;

import fun.Function;
import literature.GABC;

/**
 * Just suited for algorithms who extends GABC.
 * @author Jez
 *
 */
public class SolutionSelector {
	static Function fun = new Function();
	public static double chooseBest(double[] sol, int j, Method method,
			double vij, double vkj, double globalj, 
			double one_x, double x){
		double res = 0, initvalue = Double.MAX_VALUE, temp;
		String factorName = null, fn1 = null, fn2 = null;
		try {
			Method[] ms = getAllMethods(FactorLib.class);
			for (int i = 0; i < ms.length; i++) {
				for (int j1 = 0; j1 < ms.length; j1++) {
					temp = calSolution(sol, j, method, vij, vkj, globalj, one_x, x, 
							ms[i].getName(), ms[j1].getName());
					if(initvalue > temp){
//						initvalue = temp;
						fn1 = ms[i].getName();
						fn2 = ms[j1].getName();
						factorName = fn1 + "_" + fn2;
					}
				}
			}
			res = calSolutionJ(vij, vkj, globalj, one_x, x, fn1, fn2);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(factorName);
		return res;
	}
	
	public static double chooseBestOnSameFactor(double[] sol, int j, Method method,
			double vij, double vkj, double globalj, 
			double one_x, double x){
		double res = 0, initvalue = Double.MAX_VALUE, temp;
		String factorName = null, fn = null;
		try {
			Method[] ms = getAllMethods(FactorLib.class);
			for (int i = 0; i < ms.length; i++) {
				temp = calSolution(sol, j, method, vij, vkj, globalj, one_x, x, 
						ms[i].getName(), ms[i].getName());
				if(initvalue > temp){
					fn = ms[i].getName();
					factorName = fn + "_" + fn;
				}
			}
			res = calSolutionJ(vij, vkj, globalj, one_x, x, fn, fn);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(factorName);
		return res;
	}
	
	public static double calSolution(double[] sol, int j, Method method, 
			double vij, double vkj, double globalj,	double one_x, double x, 
			String factor1, String factor2) throws Exception {
		double[] solCopy = Arrays.copyOf(sol, sol.length);
		solCopy[j] = calSolutionJ(vij, vkj, globalj, one_x, x, factor1, factor2);
		double res = (double)(method.invoke(fun, solCopy));
		return res;
	}
	
	
	public static double calSolutionJ(double vij, double vkj, double globalj, 
			double one_x, double x, String factor1, String factor2){
		one_x = getValue(factor1, one_x);
		x = getValue(factor2, x);
		double r = ((double)Math.random()*32767 / ((double)(32767)+(double)(1)));
		double sol = vij +(vij - vkj)*(r-0.5)*2*one_x + GABC.alpha*x*(globalj-vij);
		return sol;
	}
	
	public static double getValue(String factor1, double input){
		double res = 0;
		try {
			Method method = FactorLib.class.getMethod(factor1, double.class);
			res =(double)(method.invoke(FactorLib.class, input));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static Method[] getAllMethods(Class<?> clazz) throws Exception{
		Method[] ms = clazz.getDeclaredMethods();
		return ms;
	}
	
	public static void main(String[] args) {
		try {
//			System.out.println(getValue("pdot1", 0.1));
			Method[] ms = getAllMethods(FactorLib.class);
			for (Method method : ms) {
				System.out.println(method.getName());
				System.out.println(method.invoke(FactorLib.class, 1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
