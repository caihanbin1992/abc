package improve;

import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import abc.ABC;
import fun.Function;

/**
 * Initialize the population with golden cut.
 * @author Jez
 *
 */
public  class ABC_GC extends ABC{



	

	public ABC_GC(String fun, int d2, int mc, double lb, double ub) {
		super(fun, d2, mc, lb, ub);
	}

	protected void init(int index)
	{
	   int j;
	   for (j=0;j<D;j++)
			{
	        r = (   (double)Math.random()*32767 / ((double)32767+(double)(1)) );
	        Foods[index][j]=r*(ub-lb)*0.618+lb;
			solution[j]=Foods[index][j];
			}
		f[index]=calculateFunction(solution);
		fitness[index]=CalculateFitness(f[index]);
		trial[index]=0;
	}


	 
}
