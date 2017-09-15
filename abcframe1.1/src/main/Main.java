package main;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import factorlib.FactorLib;
import factorlib.SolutionSelector;

public class Main {
	public static String imp = "src/improve/";
	public static String lit = "src/literature/";
	
	public static void main(String[] args) {
//		getABC("GABC", "sphere", 2, 2500);
//		String[] alists = {"GABCL2L2Sdot1","GABCL2L2Sdot25","GABCL2L2Sdot5","GABCL2L2Sdot75",
//				"GABCL2L2S","GABCL2L2S1dot5","GABCL2L2S2dot0","GABCL2L2S5dot0","GABCL2L2S10dot0"};
		String[] alists = {"GABCL2L2SD", "GABCL2L2SD_FLSame","GABCL2L2S_FLSame"};//"GABCL2L2S_FL",
		Set<String> impClasses = getClassNameFromDir(imp, false),
				litClasses = getClassNameFromDir(lit, false),
				allAlgos = new HashSet<String>();
		litClasses.add("ABC");
		allAlgos.addAll(impClasses);
		allAlgos.addAll(litClasses);
		
		Run t = new Run();
		try {
//			t.abc("ABC");
//			t.abc("AABCLS");
//			t.abc("GABCS");
//			t.abc("LABCSS");
//			t.abc("GABCL2L2SD");
//			t.abc("GABCL2L2S2dot0");
//			t.abc("GABCL2L2S10dot0");
//			t.abc("GABC");
//			t.abc("GABCL2");
//			t.abc("BSFABC");
//			t.abc("GABCL2L2S");
//			t.abc("GABCL2L2");
//			t.abc("IABC");
//			t.abc("ABC_GC");
//			t.abc("HGABC");
//			for (String string : alists) {
//				t.abc(string);
//			}
//			System.out.println(getClassNameFromDir(imp, false));
//			runAll(allAlgos);
			runAllFactors("GABCL2L2S_Factor");
//			runAllFactors("GABCL2L2SD_Factor");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void runAll(Set<String> as){
		Thread thread = null;
		for (String a : as) {
			thread = new Thread(new Run(a));
			thread.setName(a);
			thread.start();
		}
	}
	
	public static void runAllFactors(String algorithm) throws Exception{
		Method[] ms = SolutionSelector.getAllMethods(FactorLib.class);
		Thread thread = null;
		for (Method m1 : ms) {
			for (Method m2 : ms) {
				thread = new Thread(new RunFactor(algorithm, m1.getName(), m2.getName()));
				thread.setName(m1.getName() + "_" + m2.getName());
				thread.start();
			}
		}
	}
	
	private static Set<String> getClassNameFromDir(String filePath, boolean isRecursion) {
		Set<String> className = new HashSet<String>();
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (File childFile : files) {
			if (childFile.isDirectory()) {
				if (isRecursion) {
					className.addAll(getClassNameFromDir(childFile.getPath(), 
							isRecursion));
				}
			} else {
				String fileName = childFile.getName();
				if (fileName.endsWith(".java") && !fileName.contains("$")) {
					className.add(fileName.replace(".java", ""));
				}
			}
		}

		return className;
	}

}
