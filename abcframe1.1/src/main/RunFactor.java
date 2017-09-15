package main;

import java.lang.reflect.Constructor;

import abc.ABC;


public class RunFactor extends Run{
//修改算法名称  加入factor， 考虑输出的文件名
	String factor1, factor2;
	String algorithmName = "GABCL2L2SD_Factor";
	
	public RunFactor(String algorithm, String factor1, String factor2){
		super(algorithm);
		this.factor1 = factor1;
		this.factor2 = factor2;
		algorithmName = algorithm + "_" + factor1 + "_" + factor2;
	}
	@Override
	public void run(){
		try {
			abc(algorithm, factor1, factor2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void abc(String algorithm, String factor1, String factor2) throws Exception{
		String a = algorithmName;
		String b = a + "******************************" + a;
		System.out.println(b);
		NoteWriter.write(b + "\r\n", outputfile+a+".txt", true);
		double lb, ub; int maxCycle, D; String function;
		ABC abc;
		for (String f : funNames) {
			function = f.substring(0, f.indexOf("."));
			D = Integer.valueOf(f.substring(f.lastIndexOf(".")+1, f.length()-1));
			lb = Double.valueOf(PropertyReader.GetValueByKey(pp, function+"."+"lb"));
			ub = Double.valueOf(PropertyReader.GetValueByKey(pp, function+"."+"ub"));
			maxCycle = Integer.valueOf(PropertyReader.GetValueByKey(pp, f+"."+"maxCycle"));
			abc = getABC(algorithm, function, D, maxCycle, lb, ub,factor1, factor2);
//			abc.setFactors(factor1, factor2);
			runABC(abc);
//			System.out.println(f + "->" + lb + "," + ub + "," + maxCycle);
		}
		NoteWriter.write("\r\n\r\n", outputfile+a+".txt", true);
	}
	
	public static ABC getABC(String algorithm, String function, int D, int maxCycle,
			double lb, double ub, String factor1, String factor2) {
		ABC bee = null;
		Class<?> clazz = getClazz(algorithm);
		
		try {
			Class<?>[] paramTypes = {String.class, int.class, int.class, double.class, 
					double.class, String.class, String.class};
			Object[] params = {function, D, maxCycle, lb, ub, factor1, factor2};
			Constructor<?> con = clazz.getConstructor(paramTypes);
			bee = (ABC)con.newInstance(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(bee.getClass().getName());
		return bee;
	}
	public void resultOutpur(ABC bee, double mean) throws Exception{
		/*String a = bee.getClass().getName();
		a = a.substring(a.lastIndexOf(".")+1,a.length());*/
		System.out.println(algorithmName + "->" + bee.fun + " D="+ bee.D + " Means  of "+bee.runtime+"runs: "+mean +
				" SD is " + sd(mean, bee.GlobalMins, bee.runtime));
		NoteWriter.write(bee.fun + " D="+ bee.D + " "+mean +
				" " + sd(mean, bee.GlobalMins, bee.runtime) + "\r\n",
				outputfile + algorithmName + ".txt", true);
	}
	

}
