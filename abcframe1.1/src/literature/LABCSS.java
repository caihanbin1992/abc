package literature;

import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import abc.ABC;
import fun.Function;

/**
 * S. Saxena, K. Sharma, S. Shiwani, and H. Sharma, "Lbest Artificial Bee 
 * Colony using Structured Swarm,"
 * @author Jez
 *
 */
public  class LABCSS extends ABC{

	int MSG = 10;  // the maximum number of subgroups
	int GlobalCount = 0;
	int GlobalLimit = 25;//The range of GlobalLimit is [SN/MSG,SN].
	double pr = 0.1; // The range of pr is [0.1,0,8].
	double r1,rpr;
	int[][] groups;

	public LABCSS(String fun, int d2, int mc, double lb, double ub) {
		super(fun,d2,mc,lb,ub);
		initGroups();
	}
	
	void initGroups(){
		groups = new int[1][FoodNumber];
		for(int i=0; i<FoodNumber; i++){
			groups[0][i] = i;
		}
	}


	/*The best food source is memorized*/
	public void MemorizeBestSource() 
	{
	   int i,j;
	    boolean update = false;
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
			if(groups.length<MSG){
				subGroups();
			}
			else {
				initGroups();
			}
		}
		pr = pr + (0.8-0.1)/maxCycle;
	 }

	
	private void subGroups() {
		int groupSize = groups.length + 1;
		int[][] newGroups = new int[groupSize][];
		int remainder = FoodNumber%groupSize;
		int sGroupSize1, sGroupSize2;
		if(0 == remainder) {
			sGroupSize1 = FoodNumber/groupSize;
			for(int i=0;i<groupSize;i++){
				newGroups[i] = new int[sGroupSize1];
				for (int j = 0; j < sGroupSize1; j++) {
					newGroups[i][j] = i*sGroupSize1+j;
				}
			}
		}
		else {
			int temp = FoodNumber;
			while (temp%groupSize != 0) {
				temp++;
			}
			sGroupSize1 = temp/groupSize;
			sGroupSize2 = sGroupSize1 - (temp - FoodNumber);
			for(int i=0;i<groupSize;i++){
				if(i != (groupSize-1)){
					newGroups[i] = new int[sGroupSize1];
					for (int j = 0; j < sGroupSize1; j++) {
						newGroups[i][j] = i*sGroupSize1+j;
					}
				}
				else{
					newGroups[i] = new int[sGroupSize2];
					for (int j = 0; j < sGroupSize2; j++) {
						newGroups[i][j] = i*sGroupSize2+j;
					}
				}
			}
		}
		
		
	}

	public void SendEmployedBees()
	{
	  int i,j;
	  /*Employed Bee Phase*/
	   for (i=0;i<FoodNumber;i++)
	   {
	        /*The parameter to be changed is determined randomly*/
	        /*r = ((double) Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        param2change=(int)(r*D);*/
	        
	        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);

	        /*Randomly selected solution must be different from the solution i*/        
//	        while(neighbour==i)
//	        {
//		        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
//		        neighbour=(int)(r*FoodNumber);
//	        }
	        for(j=0;j<D;j++){
	        	solution[j]=Foods[i][j];
	        	r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        	r1 = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        	rpr = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        	if(rpr>=pr){
	        		solution[j]=Foods[i][j]+
	        				(Foods[i][j]-Foods[neighbour][j])*(r-0.5)*2+
	        				r1*(GlobalParams[j] - Foods[i][j]);
	        	}
	        	else {
					solution[j] = Foods[i][j];
				}
	        	
	        	/*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
	        	if (solution[j]<lb)
	        		solution[j]=lb;
	        	if (solution[j]>ub)
	        		solution[j]=ub;
	        }
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

	public void SendOnlookerBees(int iter){
		SendOnlookerBees();
	}

	public void SendOnlookerBees()
	{
		int temp = FoodNumber, sGroupSize1;
		while (temp%groups.length != 0) {
			temp++;
		}
		sGroupSize1 = temp/groups.length;
		int whichGroup;

	  int i,j,t;
	  i=0;
	  t=0;
	  /*onlooker Bee Phase*/
	  while(t<FoodNumber)
	  {
			whichGroup = t/sGroupSize1;

	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        if(r<prob[i]) /*choose a food source depending on its probability to be chosen*/
	        {        
		        t++;
		        
		        /*The parameter to be changed is determined randomly*/
		        r = ((double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        param2change=(int)(r*D);
		        
		        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
		        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        neighbour=groups[whichGroup][(int)(r*sGroupSize1)];
	
		        /*Randomly selected solution must be different from the solution i*/        
		        while(neighbour == i)
		        {
		        	//System.out.println(Math.random()*32767+"  "+32767);
			        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
			        neighbour=groups[whichGroup][(int)(r*sGroupSize1)];
		        }
		        for(j=0;j<D;j++)
		        	solution[j]=Foods[i][j];
	
		        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
		        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        r1 = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        solution[param2change]=Foods[i][param2change]+
		        		(Foods[neighbour][param2change]-Foods[i][param2change])*(r-0.5)*2+
		        		r1*(GlobalParams[param2change] - Foods[i][param2change]);
	
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
