package fun;

import java.util.Arrays;

public class Function {
	public double Sphere(double sol[])
	{
		int j;
		double top=0;
		for(j=0;j<sol.length;j++)
		{
			top=top+sol[j]*sol[j];
		}
		return top;
	}
	
	public double Rosenbrock(double sol[])
	{
		int j;
		double top=0;
		for(j=0;j<sol.length-1;j++)
		{
			top=top+100*Math.pow((sol[j+1]-Math.pow((sol[j]),(double)2)),(double)2)
					+Math.pow((sol[j]-1),(double)2);
		}
		return top;
	}

	 public double Griewank(double sol[])
	 {
		 int j;
		 double top1,top2,top;
		 top=0;
		 top1=0;
		 top2=1;
		 for(j=0;j<sol.length;j++)
		 {
			 top1=top1+Math.pow((sol[j]),(double)2);
			 top2=top2*Math.cos((((sol[j])/Math.sqrt((double)(j+1)))*Math.PI)/180);

		 }	
		 top=(1/(double)4000)*top1-top2+1;
		 return top;
	 }

	 public double Rastrigin(double sol[])
	 {
		 int j;
		 double top=0;

		 for(j=0;j<sol.length;j++)
		 {
			 top=top+(Math.pow(sol[j],(double)2)-10*Math.cos(2*Math.PI*sol[j])+10);
		 }
		 return top;
	 }
	 public double Elliptic(double sol[]){
			double top=0;
			for(int i=0;i<sol.length;i++){
				top=top+Math.pow((double)1000000, (double)((i)/(sol.length-1)))*sol[i]*sol[i];
			}
			return top;
		}
	 public double SumSquare(double sol[]){
		 double top=0;
		 for(int i=0;i<sol.length;i++){
			 top=top+(i+1)*sol[i]*sol[i];
		 }
		 return top;
	 }
	 public double SumPower(double sol[]){
		 double top=0;
		 for(int i=0;i<sol.length;i++){
			 top=top+Math.pow(Math.abs(sol[i]),(double)(i+2));
		 }
		 return top;
	 }
	 public double Schwefel2(double sol[]){
		 double top=0;
		 double top1=0;
		 double top2=1;
		 for(int i=0;i<sol.length;i++){
			 top1=top1+Math.abs(sol[i]);
			 top2=top2*Math.abs(sol[i]);
		 }
		 top=top1+top2;
		 return top;
	 }
	 public double Schwefel1(double sol[]){
		 
		 double top=0;
		 double[] arr=new double[sol.length];
		 for(int i=0;i<sol.length;i++){
			 arr[i]=Math.abs(sol[i]);
		 }
		 Arrays.sort(arr);
		 top=arr[sol.length-1];
		return top;	 
	 }
	 public double Exponential(double sol[]){
		 double top=0;
		 double top1=0;
		 for(int i=0;i<sol.length;i++){
			 top1=top1+sol[i];
		 }
		 top=Math.exp(0.5*top1)-1;
		 return top;
	 }
	 public double Quartic(double sol[]){
		 double top=0;
		 double top1=0;
		 for(int i=0;i<sol.length;i++){
			 top=top+(i+1)*Math.pow(sol[i],(double)(4));
		 }
		top1=top+(double)Math.random();
		return top1;
	 }
	 public double NCRastrigin(double sol[]){
		 double top=0;
		 for(int i=0;i<sol.length;i++){
			 if(Math.abs(sol[i])<0.5){
				 top=top+sol[i]*sol[i]-10*Math.cos(2*Math.PI*sol[i])+10;
			 }else{
				 top=top+(Math.round(2*sol[i])/2)*(Math.round(2*sol[i])/2)-10*Math.cos(2*Math.PI*(Math.round(2*sol[i])/2))+10;
			 }
		 }
		 return top;
	 }
	 public double Schwefel6(double sol[]){//check it
		 double top=0;
		 double top1=0;
		 for(int i=0;i<sol.length;i++){
			 top=top+sol[i]*Math.sin(Math.sqrt(Math.abs(sol[i])));
		 }
		 top1=418.9829*sol.length-top;//418.98288727243369
		 return top1;
	 }
	 public double Ackley(double sol[]){
		 double top = 0, top1 = 0, top2 = 0;
		 int size = sol.length;
		 for (int i = 0; i < sol.length; i++) {
			top1 += sol[i]*sol[i];
			top2 += Math.cos(2*Math.PI*sol[i]);
		 }
		 top1 = -0.2 * Math.sqrt(top1/(double)size);
		 top1 = Math.exp(top1) * (double)(-20);
		 top2 = Math.exp(top2/(double)size);
		 top = Math.exp(1) - top2 + 20 + top1 ;
//		 top = 20 + top1;
//		 top = top + Math.exp(1);
//		 top = top -top2;
		 return top;
	 }
	 public double Alpine(double sol[]){
		 double top=0;
		 for(int i=0;i<sol.length;i++){
			 top=top+Math.abs(sol[i]*Math.sin(sol[i])+0.1*sol[i]);
		
	 }
		 return top;
	 }
	 public double Himmelblau(double sol[]){
		 double top=0;
		 double top1=0;
		 for(int i=0;i<sol.length;i++){
			 top1=top1+Math.pow(sol[i], (double)4)-16*sol[i]*sol[i]+5*sol[i];
		 }
		 top=top1/sol.length;
		 return top;
	 }
	 //角度是否需要换算
	 public double Michalewicz(double sol[]){
		 double top=0;
		 for(int i=0;i<sol.length;i++){
			 top=top+Math.sin(sol[i])*Math.pow(Math.sin((i+1)*sol[i]*sol[i]/Math.PI),(double)20);
		 }
		 return -top;
	 }
	 
	 public double Levy(double sol[]){//PI 精度有问题
		 double top=0;
		 double top1=0;
		 for(int i=0;i<sol.length-1;i++){
			 top1=top1+(sol[i]-1)*(sol[i]-1)*(1+Math.pow(Math.sin(3*Math.PI*sol[i+1]), (double)2));
		 }
//		 System.out.println("Levy->top1 " + top1);
		 top = Math.pow(Math.sin(3*Math.PI*sol[0]),(double)2);
//		 System.out.println(top);
		 top += Math.abs(sol[sol.length-1]-1)
				 *(1+Math.pow(Math.sin(3*Math.PI*sol[sol.length-1]), (double)2));
//		 System.out.println(top);
		 top += top1;
//		 System.out.println(top);
		 return top;
	 }
	 
	 public double Weierstrass(double sol[]){//PI  精度有问题
		 double top=0;
		 double top1=0;
		 double top2=0;
		 double a=0.5;
		 int b=3;
		 int k=20;
		 for(int i=0;i<sol.length;i++){
			 for(int j=0;j<=k;j++){
				 top1=top1+Math.pow(a, (double)k)*Math.cos(2*Math.PI*Math.pow((double)b, (double)k)*(sol[i])+0.5);
				 
			 }
		 }
		 for(int i=0;i<=k;i++){
			 top2=top2+Math.pow(a, (double)k)*Math.cos(2*Math.PI*Math.pow((double)b, (double)k)*0.5);
		 }
		 top=top1-sol.length*top2;
		 return top;
	 }
	 
	 public double step(double sol[]){
		 double top=0;
		 for(int i=0;i<sol.length;i++){
			 top=top+Math.floor(sol[i]+0.5)*Math.floor(sol[i]+0.5);
		 }
		 return top;
	 }
	 public double Penalized1(double sol[]){////PI  精度有问题
		 double top=0;
		 double top1=0;
		 double top2=0;
		 int a=10,k=100,m=4;
		 for(int i=0;i<sol.length-1;i++){
			 top1=top1+(sol[i]+1)*(sol[i]+1)/16*(1+10*Math.pow(Math.sin(Math.PI*sol[i+1]),(double)2));
			 if(sol[i]>a){
				 top2=top2+k*Math.pow((sol[i]-a),(double)m);
			 }else if(sol[i]<-a){
				 top2=top2+k*Math.pow((-sol[i]-a),(double)m);
			 }else{
				 top2=top2+0;
			 }
		 }
		 top=Math.PI/sol.length*(10*Math.pow(Math.sin(Math.PI)*(1+(sol[0]+1)/4),(double)2)+top1+Math.pow(sol[sol.length-1]+1, (double)2)/16)+top2;
		 return top;
	 }
	 public double Penalized2(double sol[]){//PI  精度有问题
		 double top=0;
		 double top1=0;
		 double top2=0;
		 int a=5,k=100,m=4;
		 for(int i=0;i<sol.length-1;i++){
			 top1=top1+(sol[i]-1)*(sol[i]-1)*(1+Math.pow(Math.sin(3*Math.PI*sol[i+1]),(double)2));
			 if(sol[i]>a){
				 top2=top2+k*Math.pow((sol[i]-a),(double)m);
			 }else if(sol[i]<-a){
				 top2=top2+k*Math.pow((-sol[i]-a),(double)m);
			 }else{
				 top2=top2+0;
			 }
		 }
		 top=0.1*(Math.pow(Math.sin(Math.PI*sol[0]), (double)2)+top1+Math.pow(sol[sol.length-1]-1, (double)2))+top2;
		 return top;
	 }
	 
	 public double Schaffer(double sol[]){
		 int size = sol.length;
		 double top = 0, top1 = 0;
		 for (int i = 0; i < size; i++) {
			top1 += sol[i] * sol[i];
		 }
		 top = Math.pow(Math.sin(Math.sqrt(top1)), (double)2)-0.5;
		 top /= Math.pow(1 + 0.001 * top1, (double)2);
		 top += 0.5;
		 return top;
	 }
	 
/*	 public static void main(String[] args) throws 
	 	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		double[] s1 = {0, 0, 0};
		double[] s2 = {1, 1, 1};
		double[] s3 = {420.9687, 420.9687, 420.9687},s4 = {-1,-1,-1};
		
		Function f = new Function();
		Method method[] = Function.class.getDeclaredMethods();
		String methodName = null;
		for (int i = 0; i < method.length; i++) {
			methodName = method[i].getName();
			if("main".equals(methodName)){
				continue;
			}
			if("Rosenbrock".equals(methodName)){
				System.out.println(methodName + "->" + method[i].invoke(f, s2));
				continue;
			}
			if("Schwefel6".equals(methodName)){
				System.out.println(methodName + "->" + method[i].invoke(f, s3));
				continue;
			}
			if("Penalized2".equals(methodName)){
				System.out.println(methodName + "->" + method[i].invoke(f, s2));
				continue;
			}
			if("Penalized1".equals(methodName)){
				System.out.println(methodName + "->" + method[i].invoke(f, s4));
				continue;
			}
			if("Levy".equals(methodName)){
				System.out.println(methodName + "->" + method[i].invoke(f, s2));
				continue;
			}
			
			System.out.println(methodName + "->" + method[i].invoke(f, s1));
		}
		try {
			System.out.println(method[1].invoke(f, s1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			f.test("sphere", s1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	 }*/
	 
	 /*public void test(String fun, double[] s) throws Exception{
		 Method method = Function.class.getMethod(fun, double[].class);
		 System.out.println(fun + "->" + method.invoke(this, s));
	 }*/
	 
}


