package comm;

import java.util.List;

public class Double2String {
	public static String Array2String(double[] num,int ws) {
		String str="";
		for (int i = 0; i < num.length; i++) {
			str=str+String.format("%."+ws+"f",num[i]);
			if(i<num.length-1){
				str+=",";
			}
		}
		return str;
	}
}
