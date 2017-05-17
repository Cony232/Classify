package comm;

import java.util.List;

public class String2Array {
	public static int[] StrArray2IntArray(String[] termlist ){
		int[] array = new int[termlist.length];  
		for(int i=0;i<termlist.length;i++){  
			array[i]=Integer.parseInt(termlist[i]);   
		}	
		return array;
	}
	
	
	public static int[] StrList2IntArray(List<String> termlist ){
		int[] array = new int[termlist.size()];  
		for(int i=0;i<termlist.size();i++){  
			array[i]=Integer.parseInt(termlist.get(i));   
		}	
		return array;
	}
	
	public static double[] StrArray2DouArray(String[] termlist ){
		double[] array = new double[termlist.length];  
		for(int i=0;i<termlist.length;i++){  
			array[i]=Double.parseDouble(termlist[i]);   
		}	
		return array;
	}
	
	public static double[] StrList2DouArray(List<String> termlist ){
		double[] array = new double[termlist.size()];  
		for(int i=0;i<termlist.size();i++){  
			array[i]=Double.parseDouble(termlist.get(i));     
		}	
		return array;
	}
}
