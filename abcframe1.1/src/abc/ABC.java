package abc;

import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import fun.Function;

public  class ABC{



	/* Control Parameters of ABC algorithm*/
	int NP=20; /* The number of colony size (employed bees+onlooker bees)*/
	protected int FoodNumber = NP/2; /*The number of food sources equals the half of the colony size*/
	public int maxCycle = 2500; /*The number of cycles for foraging {a stopping criteria}*/

	/* Problem specific variables*/
	public int D = 0; /*The number of parameters of the problem to be optimized*/
	public int limit = D*FoodNumber;  /*A food source which could not be improved through "limit" trials is abandoned by its employed bee*/
	protected double lb = -5.12; /*lower bound of the parameters. */
	protected double ub = 5.12; /*upper bound of the parameters. lb and ub can be defined as arrays for the problems of which parameters have different bounds*/

	public int runtime = 30;  /*Algorithm can be run many times in order to see its robustness*/

	int dizi1[]=new int[10];
	protected double Foods[][]=new double[FoodNumber][D];        /*Foods is the population of food sources. Each row of Foods matrix is a vector holding D parameters to be optimized. The number of rows of Foods matrix equals to the FoodNumber*/
	protected double f[]=new double[FoodNumber];        /*f is a vector holding objective function values associated with food sources */
	protected double fitness[]=new double[FoodNumber];      /*fitness is a vector holding fitness (quality) values associated with food sources*/
	protected double trial[]=new double[FoodNumber];         /*trial is a vector holding trial numbers through which solutions can not be improved*/
	protected double prob[]=new double[FoodNumber];          /*prob is a vector holding probabilities of food sources (solutions) to be chosen*/
	protected double solution[]=new double[D];            /*New solution (neighbour) produced by v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) j is a randomly chosen parameter and k is a randomlu chosen solution different from i*/
	
                   
	protected double ObjValSol;              /*Objective function value of new solution*/
	protected double FitnessSol;              /*Fitness value of new solution*/
	protected int neighbour;                   /*param2change corrresponds to j, neighbour corresponds to k in equation v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij})*/
	protected int param2change;

	public double GlobalMin;                       /*Optimum solution obtained by ABC algorithm*/
	public double GlobalParams[]=new double[D];                   /*Parameters of the optimum solution*/
	public double GlobalMins[]=new double[runtime];            
	         /*GlobalMins holds the GlobalMin of each run in multiple runs*/
	protected double r; /*a random number in the range [0,1)*/
	protected Method functionMethod;
	Function fff = new Function();
	protected String factor1, factor2;
	/*a function pointer returning double and taking a D-dimensional array as argument */
	/*If your function takes additional arguments then change function pointer definition and lines calling "...=function(solution);" in the code*/


//	typedef double (*FunctionCallback)(double sol[D]);  

//	public void setFactors(String f1, String f2){
//		this.factor1 = f1;
//		this.factor2 = f2;
//	}

	public String fun = "sphere";
	public ABC(){}
	
	public ABC(String fun, int d2, int mc, double lb, double ub) {
		this.D = d2;
		limit = D*FoodNumber/2;
		Foods=new double[FoodNumber][D];        /*Foods is the population of food sources. Each row of Foods matrix is a vector holding D parameters to be optimized. The number of rows of Foods matrix equals to the FoodNumber*/
		f=new double[FoodNumber];        /*f is a vector holding objective function values associated with food sources */
		fitness=new double[FoodNumber];      /*fitness is a vector holding fitness (quality) values associated with food sources*/
		trial=new double[FoodNumber];         /*trial is a vector holding trial numbers through which solutions can not be improved*/
		prob=new double[FoodNumber];          /*prob is a vector holding probabilities of food sources (solutions) to be chosen*/
		solution=new double[D];
		GlobalParams=new double[D];
		this.lb = lb;
		this.ub = ub;
		maxCycle = mc;
		this.fun = fun;
		try {
			functionMethod =  Function.class.getMethod(fun, double[].class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * This constructor is set for factor experiment.
	 * @param fun->function
	 * @param d2->dimension
	 * @param mc->maxCycle
	 * @param lb->lower bound
	 * @param ub->upper bound
	 * @param factor1
	 * @param factor2
	 */
	public ABC(String fun, int d2, int mc, double lb,
			double ub, String factor1, String factor2) {
		this(fun, d2, mc, lb, ub);
		this.factor1 = factor1;
		this.factor2 = factor2;
	}


	/*Fitness function*/
	protected double CalculateFitness(double fun) 
	 {
		 double result=0;
		 if(fun>=0)
		 {
			 result=1/(fun+1);
		 }
		 else
		 {
			 
			 result=1+Math.abs(fun);
		 }
		 return result;
	 }

	/*The best food source is memorized*/
	public void MemorizeBestSource() 
	{
	   int i,j;
	    
		for(i=0;i<FoodNumber;i++)
		{
		if (f[i]<GlobalMin)
			{
	        GlobalMin=f[i];
	        for(j=0;j<D;j++)
	           GlobalParams[j]=Foods[i][j];
	        }
		}
	 }

	/*Variables are initialized in the range [lb,ub]. If each parameter has different range, use arrays lb[j], ub[j] instead of lb and ub */
	/* Counters of food sources are also initialized in this function*/


	protected void init(int index)
	{
	   int j;
	   for (j=0;j<D;j++)
			{
	        r = (   (double)Math.random()*32767 / ((double)32767+(double)(1)) );
	        Foods[index][j]=r*(ub-lb)+lb;
			solution[j]=Foods[index][j];
			}
		f[index]=calculateFunction(solution);
		fitness[index]=CalculateFitness(f[index]);
		trial[index]=0;
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


	}

	public void SendEmployedBees(int iter){
		SendEmployedBees();
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
	        solution[param2change]=Foods[i][param2change]+(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;

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

	/* A food source is chosen with the probability which is proportioal to its quality*/
	/*Different schemes can be used to calculate the probability values*/
	/*For example prob(i)=fitness(i)/sum(fitness)*/
	/*or in a way used in the metot below prob(i)=a*fitness(i)/max(fitness)+b*/
	/*probability values are calculated by using fitness values and normalized by dividing maximum fitness value*/
	public void CalculateProbabilities()
	{
	     int i;
	     double maxfit;
	     maxfit=fitness[0];
	  for (i=1;i<FoodNumber;i++)
	        {
	           if (fitness[i]>maxfit)
	           maxfit=fitness[i];
	        }

	 for (i=0;i<FoodNumber;i++)
	        {
	         prob[i]=(0.9*(fitness[i]/maxfit))+0.1;
	        }

	}
	
	public void SendOnlookerBees(int iter){
		SendOnlookerBees();
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
	        solution[param2change]=Foods[i][param2change]+(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;

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
	public void SendScoutBees(int iter){
		SendScoutBees();
	}
	
	public void SendScoutBees()
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
			init(maxtrialindex);
		}
	}
	
	public void LocalSearch(int iter){}


	
	

	protected double calculateFunction(double sol[])
	{
		double value = Double.MAX_VALUE;
		try {
			Object object = functionMethod.invoke(fff, sol);
			value = (double)object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	 
}
