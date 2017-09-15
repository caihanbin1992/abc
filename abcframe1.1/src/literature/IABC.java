package literature;

import java.lang.Math;
import java.util.Arrays;

import abc.ABC;

/**
 * 
 * G. Li, P. Niu, and X. Xiao, "Development and investigation of efficient 
 * artificial bee colony algorithm for numerical function optimization,"
 * @author Jez
 *
 */
public  class IABC extends ABC{

	double r1;
	double ap;


	public IABC(String fun, int D, int mc, double lb, double ub) {
		super(fun, D, mc, lb, ub);
	}





	/*All food sources are initialized */
	public void initial()
	{
		int i;
		for(i=0;i<FoodNumber;i++)
		{
		init(i);
		}
		GlobalMin=f[0];
	    for(i=0;i<D;i++)
	    GlobalParams[i]=Foods[0][i];
//	    ap = Arrays.copyOf(fitness, fitness.length);
	    ap = fitness[0];
	}

	public void SendEmployedBees()
	{
	  int i,j;
	  /*Employed Bee Phase*/
	   for (i=0;i<FoodNumber;i++)
	        {
	        /*The parameter to be changed is determined randomly*/
	        r = ((double) Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        param2change=(int)(r*D);
	        
	        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);

	        /*Randomly selected solution must be different from the solution i*/        
//	        while(neighbour==i)
//	        {
//		        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
//		        neighbour=(int)(r*FoodNumber);
//	        }
	        for(j=0;j<D;j++)
	        solution[j]=Foods[i][j];

	        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        r1 = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        solution[param2change]=((double)1/(1+Math.exp(-fitness[i]/ap)))*Foods[i][param2change]
	        		+(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2
	        		+ ((double)1/(1+Math.exp(-fitness[i]/ap)))*r1*(GlobalParams[param2change]-Foods[neighbour][param2change]);

	        /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
	        if (solution[param2change]<lb)
	           solution[param2change]=lb;
	        if (solution[param2change]>ub)
	           solution[param2change]=ub;
	        ObjValSol=calculateFunction(solution);
	        FitnessSol=CalculateFitness(ObjValSol);
	        
	        /*a greedy selection is applied between the current solution i and its mutant*/
	        if (FitnessSol>fitness[i])
	        {
	        
	        /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
	        trial[i]=0;
	        for(j=0;j<D;j++)
	        Foods[i][j]=solution[j];
	        f[i]=ObjValSol;
	        fitness[i]=FitnessSol;
	        }
	        else
	        {   /*if the solution i can not be improved, increase its trial counter*/
	            trial[i]=trial[i]+1;
	        }


	        }

	        /*end of employed bee phase*/

	}

	public void SendOnlookerBees()
	{

	  int i,j,t;
	  i=0;
	  t=0;
	  /*onlooker Bee Phase*/
	  while(t<FoodNumber)
	        {

	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        if(r<prob[i]) /*choose a food source depending on its probability to be chosen*/
	        {        
	        t++;
	        
	        /*The parameter to be changed is determined randomly*/
	        r = ((double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        param2change=(int)(r*D);
	        
	        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);

	        /*Randomly selected solution must be different from the solution i*/        
	        while(neighbour == i)
	        {
	        	//System.out.println(Math.random()*32767+"  "+32767);
		        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        neighbour=(int)(r*FoodNumber);
	        }
	        for(j=0;j<D;j++)
	        solution[j]=Foods[i][j];

	        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        r1 = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        solution[param2change]=((double)1/(1+Math.exp(-fitness[i]/ap)))*Foods[i][param2change]
	        		+((double)1/(1+Math.exp(-fitness[i]/ap)))*(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2
	        		+ ((double)1/(1+Math.exp(-fitness[i]/ap)))*r1*(GlobalParams[param2change]-Foods[neighbour][param2change]);

	        /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
	        if (solution[param2change]<lb)
	           solution[param2change]=lb;
	        if (solution[param2change]>ub)
	           solution[param2change]=ub;
	        ObjValSol=calculateFunction(solution);
	        FitnessSol=CalculateFitness(ObjValSol);
	        
	        /*a greedy selection is applied between the current solution i and its mutant*/
	        if (FitnessSol>fitness[i])
	        {
	        /*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
	        trial[i]=0;
	        for(j=0;j<D;j++)
	        Foods[i][j]=solution[j];
	        f[i]=ObjValSol;
	        fitness[i]=FitnessSol;
	        }
	        else
	        {   /*if the solution i can not be improved, increase its trial counter*/
	            trial[i]=trial[i]+1;
	        }
	        } /*if */
	        i++;
	        if (i==FoodNumber)
	        i=0;
	        }/*while*/

	        /*end of onlooker bee phase     */
	}
	 
	 public static void main(String[] args) {
		double sol[] = {0, 0, 0};
//		IABC b = new IABC("sphere", 15, 2500);
//		System.out.println(Math.E - Math.exp(1));
		/*System.out.println(b.Ackley(sol));
		System.out.println(b.Schaffer(sol));
		System.out.println(b.Schwefel6(sol));
		System.out.println(b.Rastrigin(sol));
		System.out.println(b.Griewank(sol));
		System.out.println(b.sphere(sol));
		double[] sol1 = {1,1,1};
		System.out.println(b.Rosenbrock(sol1));*/
//		System.out.println(2.718281828459045-2.7182818284590455);
//		System.out.println(20-20.0);
	}
	 
}
