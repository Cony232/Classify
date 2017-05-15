package chi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import comm.Double2String;
import comm.String2IntArray;
import comm.String2Txt;
import comm.Txt2String;

public class Funcation_Chi {
	private static double N=156000;
	private static double txt[]={10000,10000,4000,10000,4000,10000,10000,10000,10000,10000,10000,4000,10000,10000,4000,10000,10000,10000};
	private static double A=0;
	private static double B=0;
	private static double C=0;
	private static double D=0;

	public static List<String> calculate_chi(List<String> txtList) {  
		List<String> featureList=new ArrayList<String>();
		List<String> maxList=new ArrayList<String>();
		for (String str : txtList) {
			double max=0;
			double[] feature=new double[18];
			int i=str.indexOf(":");
			String word=str.substring(0, i);
			String numString=str.substring(i+1);
			String[] termlist=numString.split(",");
			int[] num=String2IntArray.String2Array(termlist);
			int sum=calculate_sum(num);
			for (int j = 0; j < num.length; j++) {
				A=num[j];
				B=sum-num[j];
				C=txt[j]-A;
				D=N-A-B-C;
				//System.out.println(word+":---------"+A+";"+B+";"+C+";"+D);
				feature[j]=chi(A,B,C,D);	
				if(feature[j]>max){
					max=feature[j];
				}
			}
			String maxFeature=Double.toString(max);
			maxList.add(word+":"+maxFeature);
			String s=Double2String.Array2String(feature);
			featureList.add(word+":"+s);
		}
		String2Txt.writeFileByLines("E:\\ceping\\feature\\max_feature.txt", maxList);
		//String max=maxList.toString()
		return featureList;
	}



	public static  double chi(double A,double B,double C,double D){
		double up=N*Math.pow((A*D)-(B*C),2);
		//System.out.println(up);
		double down=(A+C)*(B+D)*(A+B)*(C+D);
		//System.out.println(down);
		return up/down;	
	}

	public static int calculate_sum(int[] array){
		int num=0;
		for (int i = 0; i < array.length; i++) {
			num=num+array[i];
		}
		return num;
	}

	public static double getMax(double[] arr){
		double max=0;
		for(int i=1;i<arr.length;i++){
			if(arr[i]>max){
				max=arr[i];
			}
		}
		return max;
	}

	public static List<String> SortFeature(List<String> maxList){
		Map<String,Double> maxMap=new HashMap<String,Double>();
		 List<String> endList=new ArrayList<String>();
		for (String string : maxList) {
			int i=string.indexOf(":");
			String word=string.substring(0, i);
			String value=string.substring(i+1);
			double num=Double.valueOf(value).doubleValue();
			maxMap.put(word, num);
		}
		List<Map.Entry<String, Double>> infoIds = new ArrayList<Map.Entry<String, Double>>(maxMap.entrySet());
		//排序
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {
			public int compare(Map.Entry<String, Double> o1,
					Map.Entry<String, Double> o2) {
				return o2.getValue()>=o1.getValue()?1:-1;
			}
		});
		 for(Map.Entry<String,Double> mapping:infoIds){   
			 endList.add(mapping.getKey()+":"+mapping.getValue());   
        }   
		return endList;
	}



	public static void main(String[] args) {
		//		List<String> txtList=Txt2String.readFileByLines("E:\\ceping\\jieba_B\\B.txt");
		//		List<String> list=calculate_chi(txtList);	
		//		String2Txt.writeFileByLines("E:\\ceping\\feature\\feature.txt", list);

		//		
		//		String str="普法:0,0,0,1,0,0,1,0,0,1,1,0,9,0,0,0,0,0";
		//		List<String> txtList=new ArrayList<String>();
		//		txtList.add(str);
		//		List<String> list=calculate_chi(txtList);	
		//		for (String string : list) {
		//			System.out.println(string);
		//		}
		
		List<String> maxList=Txt2String.readFileByLines("E:\\ceping\\feature\\max_feature.txt");
		List<String> list=SortFeature(maxList);
		String2Txt.writeFileByLines("E:\\ceping\\feature\\end_feature.txt", list);
	}
}
