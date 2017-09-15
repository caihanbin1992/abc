package literature;

import java.lang.Math;

import abc.ABC;

/**
 * A. Banharnsakun, T. Achalakul, and B. Sirinaovakul, "The best-so-far 
 * selection in artificial bee colony algorithm,"
 * @author Jez
 *
 */
public  class BSFABC extends ABC{




	public BSFABC(String fun, int d, int mc, double lb, double ub) {
		super(fun, d, mc, lb, ub);
	}
	
	public double getBestFitness(){
		double bfit = fitness[0];
		for (int i = 1; i < fitness.length; i++) {
			if(bfit < fitness[i]){
				bfit = fitness[i];
			}
		}
		return bfit;
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
	        solution[param2change]=Foods[i][param2change]+getBestFitness() * (Foods[i][param2change]-GlobalParams[param2change])*(r-0.5)*2;

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

	/*determine the food sources whose trial counter exceeds the "limit" value. In Basic ABC, only one scout is allowed to occur in each cycle*/
	public void SendScoutBees(int iter)
	{
		int maxtrialindex,i;
		maxtrialindex=0;
		for (i=1;i<FoodNumber;i++)
        {
	         if (trial[i]>trial[maxtrialindex])
	         maxtrialindex=i;
        }
		if(trial[maxtrialindex]>=limit)
		{
			int j;
			   for (j=0;j<D;j++)
					{
				        r = (   (double)Math.random()*32767 / ((double)32767+(double)(1)) );
				        Foods[maxtrialindex][j]=Foods[maxtrialindex][j]*(1+(r-0.5)*2*(1-0.8*(double)iter/maxCycle));
						solution[j]=Foods[maxtrialindex][j];
					}
				f[maxtrialindex]=calculateFunction(solution);
				fitness[maxtrialindex]=CalculateFitness(f[maxtrialindex]);
				trial[maxtrialindex]=0;
			
		}
	}

	 
	 public static void main(String[] args) {
		double sol[] = {0, 0, 0};
		BSFABC b = new BSFABC("sphere", 15, 2500, -50, 50);
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
