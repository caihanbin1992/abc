package literature;

import java.lang.Math;

/**
 * H. Shah, T. Herawan, R. Naseem, and R. Ghazali, "Hybrid Guided Artificial Bee 
 * Colony Algorithm for Numerical Function Optimization,"
 *
 * @author Jez
 *
 */
public  class HGABC extends GABC{
	
	public HGABC(String fun, int D, int mc, double lb, double ub) {
		super(fun, D, mc, lb, ub);
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
			int j;
			for (j=0;j<D;j++)
			{
				r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
			    neighbour=(int)(r*FoodNumber);

		        while(neighbour == maxtrialindex)
		        {
		        	r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        	neighbour=(int)(r*FoodNumber);
		        }
		        r = (   (double)Math.random()*32767 / ((double)32767+(double)(1)) );
		        Foods[maxtrialindex][j]=Foods[maxtrialindex][j] + (r-0.5)*2*(Foods[maxtrialindex][j]-Foods[neighbour][j])
		        		+ (1-(r-0.5)*2)*(Foods[maxtrialindex][j]-Foods[findBestFood()][j]);
				solution[j]=Foods[maxtrialindex][j];
			}
			f[maxtrialindex]=calculateFunction(solution);
			fitness[maxtrialindex]=CalculateFitness(f[maxtrialindex]);
			trial[maxtrialindex]=0;
		}
	}
	
}
