package improve;
import java.lang.Math;

import factorlib.SolutionSelector;
import literature.GABC;

/**
 * The best so far. With dynamic population size.
 * @author Jez
 *
 */
public  class GABCL2L2SD_FL extends GABCL2L2S{
	protected int GlobalCount = 0;
	protected int GlobalLimit = 25;//The range of GlobalLimit is [SN/MSG,SN].
	protected boolean update = false;
	
	public GABCL2L2SD_FL(String fun, int d2, int mc, double lb, double ub) {
		super(fun, d2, mc, lb, ub);
	}

	/*The best food source is memorized*/
	public void MemorizeBestSource() 
	{
	   int i,j;
		for(i=0;i<FoodNumber;i++)
		{
			if (f[i]<GlobalMin)
			{
				update = true;
		        GlobalMin=f[i];
		        for(j=0;j<D;j++)
		           GlobalParams[j]=Foods[i][j];
		    }
		}
		if(!update){
			GlobalCount++;
		}
		if(GlobalCount>GlobalLimit){
			GlobalCount = 0;
			update = false;
		}
	 }
	
	public void SendEmployedBees(int iter)
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
	       // while(neighbour==i)
	       // {
	       // r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	       // neighbour=(int)(r*FoodNumber);
	       // }
	        for(j=0;j<D;j++)
	        solution[j]=Foods[i][j];

	        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        if(update){
	        	solution[param2change] = SolutionSelector.chooseBest(
		        		solution, param2change, functionMethod, Foods[i][param2change], 
		        		Foods[neighbour][param2change], GlobalParams[param2change], 
		        		((double)(maxCycle-iter)/maxCycle), ((double)iter/maxCycle));
	        }
	        else {
	        	solution[param2change]=Foods[i][param2change]+
	        			(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;
			}


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


	public void SendOnlookerBees(int iter)
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
	        if(update){
	        	solution[param2change] = SolutionSelector.chooseBest(
		        		solution, param2change, functionMethod, Foods[i][param2change], 
		        		Foods[neighbour][param2change], GlobalParams[param2change], 
		        		((double)(maxCycle-iter)/maxCycle), ((double)iter/maxCycle));
	        }
	        else {
	        	solution[param2change]=Foods[i][param2change]+
	        			(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;
			}

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
	
}
