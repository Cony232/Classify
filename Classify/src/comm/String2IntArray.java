package comm;

public class String2IntArray {
	public static int[] String2Array(String[] termlist ){
		int[] array = new int[termlist.length];  
		for(int i=0;i<termlist.length;i++){  
			array[i]=Integer.parseInt(termlist[i]);   
		}	
		return array;
	}
}
