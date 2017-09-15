package factorlib;

public class FactorLib {

	//power family
	public static double pdot1(double x){
		return Math.pow(x, 0.1);
	}
	public static double pdot2(double x){
		return Math.pow(x, 0.2);
	}
	public static double pdot25(double x){
		return Math.pow(x, 0.25);
	}
	public static double pdot5(double x){
		return Math.pow(x, 0.5);
	}
	public static double pdot75(double x){
		return Math.pow(x, 0.75);
	}
	public static double p1dot0(double x){
		return Math.pow(x, 1.0);
	}
	public static double p1dot5(double x){
		return Math.pow(x, 1.5);
	}
	public static double p2dot0(double x){
		return Math.pow(x, 2.0);
	}
	public static double p3dot0(double x){
		return Math.pow(x, 3.0);
	}
	public static double p5dot0(double x){
		return Math.pow(x, 5.0);
	}
	public static double p10dot0(double x){
		return Math.pow(x, 10.0);
	}
	
	//exponent family
	public static double exp2(double x){
		return Math.pow(2, x)-1;
	}
	public static double exp3(double x){
		return (Math.pow(3, x)-1)*1.0/2.0;
	}
	public static double exp4(double x){
		return (Math.pow(4, x)-1)*1.0/3.0;
	}
	public static double expe(double x){
		return (Math.exp(x)-1)/(Math.exp(1)-1);
	}
	
	//trigonometric function family
	public static double sin(double x){
		return Math.sin(x)/Math.sin(1);
	}
	public static double cos(double x){
		return (1-Math.cos(x))/(1-Math.cos(1));
	}
	public static double arctan(double x){
		return Math.atan(x)/Math.atan(1);
	}
	
	//logarithm family
	public static double log10(double x){
		return Math.log10(x+1)/Math.log10(2);
	}
	public static double ln(double x){
		return Math.log(x+1)/Math.log(2);
	}
	
	// hyperbolic functions family
	public static double shx(double x){
		return (Math.exp(x+1)-Math.exp(1-x))/(Math.exp(2)-1);
	}
	public static double chx(double x){
		return (Math.exp(x)+Math.exp(-x)-2)/(Math.exp(1)+Math.exp(-1)-2);
	}
	public static double thx(double x){
		double exp2 = Math.exp(2);
		return (exp2+1)/(exp2-1)*(Math.exp(2*x)-1)/(Math.exp(2*x)+1);
	}
	public static double arshx(double x){
		return Math.log(x+Math.sqrt(x*x+1))/Math.log(1+Math.sqrt(2));
	}
}
